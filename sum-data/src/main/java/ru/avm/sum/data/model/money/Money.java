package ru.avm.sum.data.model.money;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 20.02.13
 * Time: 13:38
 * To change this template use File | Settings | File Templates.
 */
public class Money extends Identity {

    private final long value;

    private final Currency currency;

    public Money(@JsonProperty("value") long value, @JsonProperty("currency") Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public long getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }
}
