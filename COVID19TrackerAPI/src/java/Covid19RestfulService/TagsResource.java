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
@Path("/tags")
public class TagsResource {

    @EJB
    private TagsBean tagsBean;

    public TagsResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getTag(@PathParam("id") String id) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        Collection<Tag> tags = tagsBean.getTag(id);
        for (Tag tag : tags) {
            arrayBuilder.add(tag.getJSONObject());
        }
        JsonObject json = jsonBuilder.add("tags", arrayBuilder).build();

        return json.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTags() {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        Collection<Tag> tags = tagsBean.getAllTags();
        for (Tag tag : tags) {
            arrayBuilder.add(tag.getJSONObject());
        }
        JsonObject json = jsonBuilder.add("tags", arrayBuilder).build();

        return json.toString();
    }
}
