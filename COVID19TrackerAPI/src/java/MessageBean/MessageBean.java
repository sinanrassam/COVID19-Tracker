/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MessageBean;

import Covid19RestfulService.Track;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sinan
 */
@MessageDriven(activationConfig
        = {
            @ActivationConfigProperty(propertyName = "destinationLookup",
                    propertyValue = "jms/MessageQueue")
            ,
        @ActivationConfigProperty(propertyName = "destinationType",
                    propertyValue = "javax.jms.Queue")

        })
public class MessageBean {
    // field obtained via dependency injection (not used here)

    @Resource
    private MessageDrivenContext mdc;
    @PersistenceContext
    private EntityManager entityManager;

    public MessageBean() {
    }

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                processMessage(((TextMessage) message).getText());
            } else {
                System.out.println("MessageBean received non-text message: " + message);
            }
        } catch (JMSException e) {
            System.err.println("Exception with incoming message: " + e);
        }
    }

    private void processMessage(String msg) {
        if (msg.startsWith(Config.MESSAGE_REASON)) {
            msg = msg.substring(Config.MESSAGE_REASON.length());
            String[] array = msg.split(" -> ");
            String username = array[0].substring(1, array[0].length() - 1);
            String location = array[1].substring(1, array[1].length() - 1);

            Track newTrack = new Track(location, username);
            entityManager.persist(newTrack); // note already in transaction
        }
    }

}
