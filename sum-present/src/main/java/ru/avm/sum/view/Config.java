package ru.avm.sum.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.view.service.SSecurity;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 21.02.13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class Config {

    /**
     * @return Bean instance
     */
    @Bean
    public ISecurity security() {
        return new SSecurity();
    }

}
