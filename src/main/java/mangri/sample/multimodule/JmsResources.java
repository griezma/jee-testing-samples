package mangri.sample.multimodule;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Queue;

@JMSDestinationDefinitions({
    @JMSDestinationDefinition(name = "java:app/jms/greet-commands", interfaceName = "javax.jms.Queue", destinationName = "greetCommandsQueue"),
    @JMSDestinationDefinition(name = "java:app/jms/greet-replies", interfaceName = "javax.jms.Queue", destinationName = "greetRepliesQueue")
})
@ApplicationScoped
public class JmsResources {
    @Resource(name = "greet/commands", lookup = "java:app/jms/greet-commands")
    Queue commandQueue;

    @Resource(name = "greet/replies", lookup = "java:app/jms/greet-replies")
    Queue repliesQueue;
}