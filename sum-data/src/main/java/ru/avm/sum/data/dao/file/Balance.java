package ru.avm.sum.data.dao.file;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.DeserializerFactory;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerFactory;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.deser.StdDeserializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.Assert;
import ru.avm.sum.data.dao.IBalance;
import ru.avm.sum.data.model.Basket;
import ru.avm.sum.data.model.Category;
import ru.avm.sum.data.model.Currency;
import ru.avm.sum.data.model.Deal;
import ru.avm.sum.data.model.Event;
import ru.avm.sum.data.model.Identity;
import ru.avm.sum.data.model.Money;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
public class Balance implements IBalance {

    private static final Logger LOG = Logger.getLogger(Balance.class);

    private static final Charset UTF_8 = Charset.forName("utf-8");

    private static final CharsetEncoder ENCODER = UTF_8.newEncoder();

    private static final CharsetDecoder DECODER = UTF_8.newDecoder();

    private static final int BUFFER_SIZE = 64 * 1024;

    private static final Pattern SPLITTER = Pattern.compile("[\n]");

    private final Map<Class<? extends Identity>, Map<String, String>> cache = new HashMap<>();

    private final String path;

    public Balance(String path) {
        this.path = path;
    }

    @Override
    public boolean insert(Basket basket) {
        return update(basket, Basket.class);
    }

    @Override
    public boolean update(Basket basket) {
        return update(basket, Basket.class);
    }

    @Override
    public boolean remove(Basket basket) {
        return remove(basket, Basket.class)
               && remove(basket.getLand(), Money.class)
               && remove(basket.getLeft(), Money.class);
    }

    @Override
    public List<Basket> total() {
        return load(Basket.class);
    }

    @Override
    public boolean insert(Deal deal) {
        return update(deal, Deal.class);
    }

    @Override
    public boolean update(Deal deal) {
        return update(deal, Deal.class);
    }

    @Override
    public boolean remove(Deal deal) {
        return remove(deal, Deal.class)
               && remove(deal.getEvent(), Event.class)
               && remove(deal.getMoney(), Money.class);
    }

    @Override
    public List<Deal> list(Date from, Date till, Basket basket, Category category) {
        List<Deal> list = load(Deal.class);
        for (Iterator<Deal> iterator = list.iterator(); iterator.hasNext(); ) {
            Deal deal = iterator.next();
            // check basket
            if ((basket != null) && !deal.getBasket().equals(basket)) {
                iterator.remove();
                continue;
            }
            Event event = deal.getEvent();
            // check category
            if ((category != null) && !category.contains(event.getCategory())) {
                iterator.remove();
                continue;
            }
            Date date = event.getDate();
            // check date
            if (date.after(till) || date.before(from)) {
                iterator.remove();
                continue;
            }
        }
        return list;
    }

    @Override
    public boolean insert(Category category) {
        return update(category, Category.class);
    }

    @Override
    public boolean update(Category category) {
        return update(category, Category.class);
    }

    @Override
    public boolean remove(Category category) {
        for (Category child : category.getChildren()) {
            if (!remove(child)) {
                LOG.warn("can't remove child " + child + " from " + category);
            }
        }
        return remove(category, Category.class);
    }

    @Override
    public List<Category> graph() {
        return load(Category.class);
    }

    @Override
    public boolean insert(Currency currency) {
        return update(currency, Currency.class);
    }

    @Override
    public boolean update(Currency currency) {
        return update(currency, Currency.class);
    }

    @Override
    public boolean remove(Currency currency) {
        return remove(currency, Currency.class);
    }

    @Override
    public List<Currency> money() {
        return load(Currency.class);
    }

    private <T extends Identity> boolean update(T value, Class<T> type) {
        if (value == null) {
            return true;
        }
        Map<String, String> table = cache.get(type);
        if (null == table) {
            cache.put(type, table = new HashMap<>());
            read(type);
        }
        try {
            table.put(value.getId(), obj2str(value));
            return write(type);
        } catch (IOException e) {
            LOG.warn("Can't write data " + value + " for " + type.getSimpleName(), e);
            return false;
        }
    }

    private <T extends Identity> boolean remove(T value, Class<T> type) {
        if (value == null) {
            return true;
        }
        Map<String, String> table = cache.get(type);
        Assert.notNull(table, "there is no table for " + type.getSimpleName());
        table.remove(value.getId());
        return write(type);
    }

    private <T extends Identity> List<T> load(Class<T> type) {
        Map<String, String> table = cache.get(type);
        if (null == table) {
            cache.put(type, table = new HashMap<>());
            read(type);
        }
        List<T> result = new ArrayList<>();
        for (String id : table.keySet()) {
            try {
                result.add(load(id, type));
            } catch (IOException e) {
                LOG.warn("Can't read data for {" + id + " : " + type.getSimpleName() + "}", e);
            }
        }
        return result;
    }

    private <T extends Identity> T load(String id, Class<T> type) throws IOException {
        Map<String, String> table = cache.get(type);
        if (null == table) {
            cache.put(type, table = new HashMap<>());
            read(type);
        }
        String value = table.get(id);
        Assert.notNull(value, "there is no data for {" + id + ", " + type.getSimpleName() + "}");
        return str2obj(value, type);
    }

    private <T extends Identity> T str2obj(String value, Class<T> type) throws IOException {
        return new ObjectMapper(null, null, new SumDeserializerProvider()).readValue(value, type);
    }

    private <T extends Identity> String obj2str(T value) throws IOException {
        return new ObjectMapper(null, new SumSerializerProvider(), null).writeValueAsString(value);
    }

    private <T extends Identity> boolean write(Class<T> type) {
        Map<String, String> table = cache.get(type);
        Assert.notNull(table, "there is no table for " + type.getSimpleName());
        try {
            // concatenate
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> item : table.entrySet()) {
                if (builder.length() > 0) {
                    builder.append('\n');
                }
                builder.append(item.getKey()).append(" : ").append(item.getValue());
            }
            // new file, get its channel
            FileChannel channel = new FileOutputStream(new File(path + '\\' + type.getName())).getChannel();
            // fill the buffer
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            ENCODER.encode(CharBuffer.wrap(builder), buffer, false); // write string bytes to buffer
            // cut buffer
            buffer.limit(buffer.position());
            // set buffer's position to 0
            buffer.rewind();
            // write to channel
            channel.write(buffer);
            channel.close();
            return true;
        } catch (IOException e) {
            LOG.warn("Can't write data for " + type.getSimpleName(), e);
            return false;
        }
    }

    private <T extends Identity> void read(Class<T> type) {
        Map<String, String> table = cache.get(type);
        Assert.notNull(table, "there is no table for " + type.getSimpleName());
        try {
            // new file, get its channel
            FileChannel channel = new FileInputStream(path + '\\' + type.getName()).getChannel();
            // allocate buffer
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size()); // allocate buffer for only string
            // read data
            channel.read(buffer);
            buffer.rewind();
            // split single string
            for (String line : SPLITTER.split(DECODER.decode(buffer).toString())) {
                table.put(line.substring(0, 36), line.substring(39));
            }
        } catch (IOException e) {
            LOG.warn("Can't read data for " + type.getSimpleName(), e);
        }
    }

    public static void main(String[] args) throws Exception {
        Balance balance = new Balance("data");
        Category category = new Category("ajksdf", Collections.singletonList(new Category("child")));
        balance.update(category);

        List<Category> data = balance.graph();
        System.err.println("result = " + data);
    }

    private class SumSerializerProvider extends StdSerializerProvider {

        private boolean root = true;

        private SumSerializerProvider() {
        }

        private SumSerializerProvider(SerializationConfig config, SumSerializerProvider src, SerializerFactory f) {
            super(config, src, f);
        }

        @Override
        protected StdSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
            return new SumSerializerProvider(config, this, jsf);
        }

        @Override
        public JsonSerializer<Object> findValueSerializer(Class<?> valueType, BeanProperty property)
                throws JsonMappingException {
            JsonSerializer serializer;
            if (root || !Identity.class.isAssignableFrom(valueType)) {
                root = false;
                serializer = super.findValueSerializer(valueType, property);
            } else {
                serializer = new IdentitySerializer();
            }
            return serializer;
        }

        @Override
        public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property)
                throws JsonMappingException {
            JsonSerializer serializer;
            if (root || !Identity.class.isAssignableFrom(valueType.getClass())) {
                root = false;
                serializer = super.findValueSerializer(valueType, property);
            } else {
                serializer = new IdentitySerializer();
            }
            return serializer;
        }

    }

    private class SumDeserializerProvider extends StdDeserializerProvider {

        private boolean root = true;

        private SumDeserializerProvider() {
        }

        private SumDeserializerProvider(DeserializerFactory f) {
            super(f);
        }

        @Override
        public StdDeserializerProvider withFactory(DeserializerFactory factory) {
            return new SumDeserializerProvider(factory);
        }

        @Override
        public JsonDeserializer<Object> findValueDeserializer(DeserializationConfig config, JavaType propertyType, BeanProperty property)
                throws JsonMappingException {
            JsonDeserializer<Object> deserializer;
            if (root || !Identity.class.isAssignableFrom(propertyType.getRawClass())) {
                root = false;
                deserializer = super.findValueDeserializer(config, propertyType, property);
            } else {
                deserializer = new IdentityDeserializer((Class) propertyType.getRawClass());
            }
            return deserializer;
        }
    }

    private class IdentityDeserializer<T extends Identity> extends JsonDeserializer<T> {

        private Class<T> _class;

        public IdentityDeserializer(Class<T> _class) {
            this._class = _class;
        }

        @Override
        public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            ObjectMapper mapper = (ObjectMapper) jp.getCodec();
            ObjectNode root = (ObjectNode) mapper.readTree(jp);
            JsonNode node = root.get("id");
            return load(node.asText(), _class);
        }
    }

    private class IdentitySerializer<T extends Identity> extends JsonSerializer<T> {

        @Override
        public void serialize(T value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            update(value, (Class<Identity>) value.getClass());
            jgen.writeStartObject();
            jgen.writeStringField("id", value.getId());
            jgen.writeEndObject();
        }
    }
}
