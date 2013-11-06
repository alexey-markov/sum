package ru.avm.sum.data.dao;

import ru.avm.sum.data.model.security.User;

/**
 * Created with IntelliJ IDEA.
 * User: AlMarkov
 * Date: 4/26/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISecurity {

    public User getUsersList(String username);

}
