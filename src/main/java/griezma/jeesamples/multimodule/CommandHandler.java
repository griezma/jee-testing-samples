package griezma.jeesamples.multimodule;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup",
            propertyValue = "java:app/jms/greet-commands"),
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Queue")
})
public class CommandHandler implements MessageListener {

    @Inject
    JMSContext jms;

    @Override
    public void onMessage(Message message) {
        try {
            Salute salute = message.getBody(Salute.class);

            String reply = invokeFriendlyServiceFake(salute.getSender(), salute.getMessage());

            jms.createProducer()
                .setJMSCorrelationID(message.getJMSCorrelationID())
                .send(message.getJMSReplyTo(), reply);
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private String invokeFriendlyServiceFake(String sender, String message) {
        return "hi " + sender;
    }
    
}
