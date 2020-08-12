package org.javamentor.spring.dao;

import org.javamentor.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DaoUserImpl implements DaoUser {

    @PersistenceContext
    @Autowired
    EntityManager em;

    @Override
    public void createNewUser(User user) {
        em.persist(user);
       // System.out.println("User " + user + " добавлен в базу данных");
    }

    @Override
    public User readUser(long id) {
        return (User) em.createQuery("from User where id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = readUser(id);
        em.remove(user);
    }

    @Override
    public List<User> usersList() {
        TypedQuery<User> query =
                em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }
}
