package ru.avm.sum.business.service;

import org.springframework.stereotype.Service;
import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.data.model.security.User;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: AlMarkov
 * Date: 4/26/13
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SSecurity implements ISecurity {

    @Resource(name = "security")
    private ISecurity security;

    public User getUsersList(String username) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
