package ru.avm.sum.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avm.sum.data.dao.ISecurity;
import ru.avm.sum.data.model.authority.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AlMarkov
 * Date: 4/26/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
@Transactional(readOnly = true)
public class CSecurity implements UserDetailsService {

    @Autowired
    private ISecurity security;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        User user = security.user(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthorityImpl(user.getAuthority().getName()));
        return new MyUser(user.getId(),
                          user.getUsername(),
                          user.getPassword(),
                          user.getEnabled(),
                          true,
                          true,
                          true,
                          authorities);
    }

    public static class MyUser extends org.springframework.security.core.userdetails.User {

        private final String id;

        public MyUser(String id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}