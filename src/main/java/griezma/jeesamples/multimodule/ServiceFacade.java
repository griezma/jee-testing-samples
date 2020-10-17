package griezma.jeesamples.multimodule;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class ServiceFacade {
    @Resource(name = "greet/commands")
    Queue commandsQueue;

    @Resource(name = "greet/replies")
    Queue replyQueue;

    @Inject
    JMSContext jms;

    @Resource
    ManagedExecutorService executor;

    private static Map<String, CompletableFuture<String>> requestFakeDb = new ConcurrentHashMap<>();

    public CompletableFuture<String> greet(String name, String message) { 
        final Salute salute = new Salute(name, message);
        return sendGreetCommand(salute);
    }

    public void handleGreetReply(String id, String reply) {
        var promise = requestFakeDb.remove(id);
        if (promise != null) {
            promise.complete(reply);
        }
    }

    private CompletableFuture<String> sendGreetCommand(Salute salute) {
        String id = salute.getID();
        var promise = new CompletableFuture<String>();
        requestFakeDb.put(id, promise);

        jms.createProducer()
            .setJMSReplyTo(replyQueue)
            .setJMSCorrelationID(id)
            .send(commandsQueue, salute);
        return promise;
    }
}
