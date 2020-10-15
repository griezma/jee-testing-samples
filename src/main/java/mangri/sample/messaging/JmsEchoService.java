package mangri.sample.messaging;

import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSProducer;
import javax.jms.Queue;

@JMSDestinationDefinition(name = "java:jboss/exported/jms/echo-queue", interfaceName = "javax.jms.Queue", destinationName = "EchoQueue")   
@Stateless
public class JmsEchoService {
    
    @Resource(name = "echo/replies", lookup =  "java:jboss/exported/jms/echo-queue")
    Queue replies;

    @Inject
    JMSContext jms;

    public void giveEchos(String message, int repeats) {
        JMSProducer jmsProd = jms.createProducer();
        Stream.iterate(message, m -> m)
            .limit(repeats)
            .forEach(m -> jmsProd.send(replies, m));
    }
}
