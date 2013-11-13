package ru.avm.sum.view.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.data.model.authority.User;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 11/11/13
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SSecurity extends RestTemplate implements ISecurity {

    @Override
    public User user(String username) {
        return postForObject("http://localhost:8080/sum-service/rest/security/users", username, User.class);
    }
}
