package ru.avm.sum.view.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ru.avm.sum.business.service.SSecurity;
import ru.avm.sum.data.model.security.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: AlMarkov
 * Date: 4/26/13
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@Transactional(readOnly = true)
public class CSecurity implements UserDetailsService {

    @Resource(name = "service")
    private SSecurity security;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        User user = security.users(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl(user.getGrantedAuthority().getRoleName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                      user.getPassword(),
                                                                      user.getEnabled() > 0,
                                                                      true,
                                                                      true,
                                                                      true,
                                                                      authorities);
    }
}