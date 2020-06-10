/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Covid19RestfulService;

import java.util.Collection;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author sinan
 */
@Named // so that dependency injection can be used for the EJB
@Path("/tracks")
public class TracksResource {

    @EJB
    private TracksBean tracksBean;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTicketsJSON() { 
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        Collection<Track> allTracks = tracksBean.getAllTracks();
        for (Track track : allTracks) {
            arrayBuilder.add(track.getJSONObject());
        }        
        JsonObject json = jsonBuilder.add("tracks", arrayBuilder).build();
        
        return json.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addNewTicket(MultivaluedMap<String, String> formParams) {
        String location = formParams.getFirst("location");
        String username = formParams.getFirst("username");
        tracksBean.addTrack(location, username);
    }
}
