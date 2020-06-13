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
public class TagsBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tag> getTag(String id) {
        String jpqlCommand = "SELECT t FROM Tag t WHERE t.id = :id";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Tag> getAllTags() {
        String jpqlCommand = "SELECT t FROM Tag t";
        Query query = entityManager.createQuery(jpqlCommand);
        return query.getResultList();
    }

}
