package mangri.sample.messaging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import static java.util.stream.Collectors.joining;

@RunWith(Arquillian.class)
public class JmsEchoServiceTest {

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war")
        .addClass(JmsEchoService.class);
    }

    @Resource(name = "echo/replies")
    Queue echoReplies;

    //@Resource(lookup = "java:global/jms/queue/Echo")
    Queue echoRepliesRemote;

    @EJB
    JmsEchoService echoService;

    @Inject
    JMSContext jms;

    @Test
    public void shouldReceiveEchoOnReplyQueue() throws Exception {
        echoService.giveEchos("jodel", 3);

        //Thread.sleep(20000);

        var echos = new ArrayList<String>();

        try (JMSConsumer jmsCons = jms.createConsumer(echoReplies)) {
            String echo;
            while ((echo = jmsCons.receiveBody(String.class, 1000)) != null) {
                echos.add(echo);
            }
        }

        assertEquals("jodel, jodel, jodel", echos.stream().collect(joining(", ")));
    }

    @Test @RunAsClient
    public void shouldFetchRemoteNamingContextOnClient() throws Exception {
        Context context = remoteNamingContext();
        assertNotNull(context);
    }

    @Test @RunAsClient
    public void shouldFetchRemoteQeueOnClient() throws Exception {
        Context naming = remoteNamingContext();
        Queue queue = (Queue) naming.lookup("jms/echo-queue");
        assertNotNull(queue);
    }

    // @Test @RunAsClient
    // public void shouldCreateRemoteJMSContextOnClient() throws Exception {
    //     Context naming = remoteNamingContext();
    //     ConnectionFactory factory = (ConnectionFactory) naming.lookup("jms/RemoteConnectionFactory");
    //     JMSContext jms = factory.createContext("clientapp", "secret");
    //     assertNotNull(jms);
    // }

    JMSContext remoteJMSContext(Context remoteNaming) throws NamingException {
        ConnectionFactory factory = (ConnectionFactory) remoteNaming.lookup("jms/RemoteConnectionFactory");
        return factory.createContext("clientapp", "secret");
    }

    Context remoteNamingContext() throws NamingException {
        String address = "localhost:8080";
        String user = "clientapp";
        String password = "secret";

        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        env.put(Context.PROVIDER_URL, "http-remoting://" + address);
        env.put(Context.SECURITY_PRINCIPAL, user);
        env.put(Context.SECURITY_CREDENTIALS, password);
        return new InitialContext(env);
    }
}
