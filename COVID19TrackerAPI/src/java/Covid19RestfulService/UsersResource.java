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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author sinan
 */
@Named // so that dependency injection can be used for the EJB
@Path("/users")
public class UsersResource {

    @EJB
    private UsersBean usersBean;

    public UsersResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{user}")
    public String getUser(@PathParam("user") String username) {        
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();       
        
        Collection<User> users = usersBean.getUser(username);
        for (User user : users) {
            arrayBuilder.add(user.getJSONObject());
        }        
        JsonObject json = jsonBuilder.add("users", arrayBuilder).build();
        
        return json.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllJSONUsers() {        
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        Collection<User> allUsers = usersBean.getAllUsers();
        for (User user : allUsers) {
            arrayBuilder.add(user.getJSONObject());
        }        
        JsonObject json = jsonBuilder.add("users", arrayBuilder).build();
        
        return json.toString();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addNewUser(MultivaluedMap<String, String> formParams) {
        String firstName = formParams.getFirst("firstName");
        String lastName = formParams.getFirst("lastName");
        String email = formParams.getFirst("email");
        String username = formParams.getFirst("username");
        String password = formParams.getFirst("password");
        usersBean.addUser(firstName, lastName, email, username, password);
    }
}