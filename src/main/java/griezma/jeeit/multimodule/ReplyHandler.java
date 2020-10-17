package griezma.jeeit.multimodule;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/greet-replies"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class ReplyHandler implements MessageListener {
    @Inject 
    ServiceFacade greetService;

    @Override
    public void onMessage(Message message) {
        try {
            String id = message.getJMSCorrelationID();
            String reply = message.getBody(String.class);
            greetService.handleGreetReply(id, reply);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
