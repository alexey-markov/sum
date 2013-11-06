package net.schastny.contactmanager.dao;

import net.schastny.contactmanager.domain.Users;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Users getUsersList(String username) {
        return (Users) sessionFactory.getCurrentSession().createQuery("from Users where username = :username")
                                     .setString("username", username).uniqueResult();
    }
}
