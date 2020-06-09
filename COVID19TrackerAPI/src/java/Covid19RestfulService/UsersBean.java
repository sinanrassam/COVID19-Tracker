/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Covid19RestfulService;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sinan
 */
@WebService // also made a web service for convenient testing
@Stateless
@LocalBean
public class UsersBean {

    @PersistenceContext
    private EntityManager entityManager;

    public User addUser(String firstName, String lastName, String email, String username, String password) {
        User newUser = new User(firstName, lastName, email, username, password);
        entityManager.persist(newUser); // note already in transaction
        return newUser;
    }

    public List<User> getUser(String username) {
        String jpqlCommand = "SELECT u FROM User u WHERE u.username = :username";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("username", username);
        return query.getResultList();
    }

    public List<User> getAllUsers() {
        String jpqlCommand = "SELECT u FROM User u";
        Query query = entityManager.createQuery(jpqlCommand);
        return query.getResultList();
    }

}
