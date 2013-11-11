package ru.avm.sum.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.avm.sum.business.service.SSecurity;
import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.data.dao.file.Security;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 21.02.13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@PropertySource("classpath:sum-application.properties")
public class Config {

    @Resource
    private Environment env;

    /**
     * @return Bean instance
     */
    @Bean
    public ISecurity service() {
        return new SSecurity();
    }

    /**
     * @return Bean instance
     */
    @Bean
    public ISecurity security() {
        return new Security(env.getProperty("sum.data.file.path.root"));
    }

}
