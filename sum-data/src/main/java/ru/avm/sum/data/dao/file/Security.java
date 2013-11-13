package ru.avm.sum.data.dao.file;

import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.data.model.authority.User;

/**
 * Created with IntelliJ IDEA.
 * User: AlMarkov
 * Date: 4/26/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Security extends AFileStore implements ISecurity {

    public Security(String root) {
        super(root);
    }

    @Override
    public User user(String username) {
        for (User user : load(User.class)) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }
}
