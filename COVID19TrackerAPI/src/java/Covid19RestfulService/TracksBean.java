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
@WebService
@Stateless
@LocalBean
public class TracksBean {

    @PersistenceContext
    private EntityManager entityManager;

    public Track addTrack(String location, String username) {
        Track newTrack = new Track(location, username);
        entityManager.persist(newTrack); // note already in transaction
        return newTrack;
    }

    public Track getTicket(Integer id) {
        TrackPK primaryKey = new TrackPK(id);
        Track track = entityManager.find(Track.class, primaryKey);
        return track;
    }

    public List<Track> getAllTracks() {
        String jpqlCommand = "SELECT t FROM Track t ORDER BY t.checkInTimestamp DESC";
        Query query = entityManager.createQuery(jpqlCommand);
        return query.getResultList();
    }

    public List<Track> getTracksForUser(String username) {
        String jpqlCommand = "SELECT t FROM Track t WHERE t.username = :username ORDER BY t.checkInTimestamp DESC";
        Query query = entityManager.createQuery(jpqlCommand);
        query.setParameter("username", username);
        return query.getResultList();
    }
}