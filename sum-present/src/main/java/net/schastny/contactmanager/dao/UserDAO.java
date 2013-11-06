package net.schastny.contactmanager.dao;

import net.schastny.contactmanager.domain.Contact;
import net.schastny.contactmanager.domain.Users;

import java.util.List;

public interface UserDAO {

    Users getUsersList(String username);

}