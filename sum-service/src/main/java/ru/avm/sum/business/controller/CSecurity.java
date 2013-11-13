package ru.avm.sum.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.data.model.authority.User;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: almarkov
 * Date: 11/11/13
 * Time: 6:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/rest" + CSecurity.BASE_URI)
public class CSecurity implements ISecurity {

    public static final String BASE_URI = "/security";

    public static final String USERS_URI = "/users";

    @Resource(name = "authority")
    private ISecurity authority;

    @Override
    @RequestMapping(value = CSecurity.USERS_URI, method = RequestMethod.POST)
    @ResponseBody
    public User user(@RequestBody String username) {
        return authority.user(username);
    }
}
