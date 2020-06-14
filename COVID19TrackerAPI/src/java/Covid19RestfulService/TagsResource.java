/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Covid19RestfulService;

import MessageBean.Config;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author sinan
 */
@Named // so that dependency injection can be used for the EJB
@Path("/tags")
public class TagsResource {

    @EJB
    private TagsBean tagsBean;

    private Connection conn;
    private Session session;
    private MessageProducer producer;
    // static fields obtained via dependency injection in Enterprise
    // Application Client
    @Resource(mappedName = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/MessageQueue")
    private Queue queue;

    public TagsResource() {
    }

    @PostConstruct
    public void prepareToSendMessage() {
        try {
            // obtain a connection to the JMS provider
            conn = connectionFactory.createConnection();
            // obtain an untransacted context for producing messages
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // obtain a producer of messages to send to the queue
            producer = session.createProducer(queue);
        } catch (JMSException e) {
            System.err.println("Unable to open connection: " + e);
        }
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
    @Path("{id}/{user}")
    public String getTagAndUpdateLocation(@PathParam("id") String id, @PathParam("user") String username) {
        JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        Collection<Tag> tags = tagsBean.getTag(id);
        if (tags.size() > 0) {
            Tag tag = (Tag) tags.toArray()[0];
            arrayBuilder.add(tag.getJSONObject());
            String location = tag.getLocation();
            sendMessage(Config.MESSAGE_REASON + "[" + username + "] -> [" + location + "]");
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

    private void sendMessage(String msg) {
        try {
            // create and send a string message
            TextMessage message = session.createTextMessage();
            message.setText(msg);
            producer.send(message);
        } catch (JMSException e) {
            System.err.println("Unable to send message: " + e);
        }

    }
}
