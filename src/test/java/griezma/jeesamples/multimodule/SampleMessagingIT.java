package griezma.jeesamples.multimodule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Queue;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import griezma.jeesamples.multimodule.ServiceFacade;

@RunWith(Arquillian.class)
public class SampleMessagingIT {

    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war")
            .addPackage(ServiceFacade.class.getPackage())
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @EJB
    ServiceFacade service;

    @Resource(name = "greet/commands")
    Queue commandsQueue;

    @Resource(name = "greet/replies")
    Queue repliesQueue;

    @Test
    public void queuesShouldBeenCreated() {
        assertNotNull(commandsQueue);
        assertNotNull(repliesQueue);
    }

    @Test
    public void serviceShouldRespondAsync() throws Exception {
        var response = service.greet("dude", "try hard");
        //response.whenComplete((a, b) -> System.out.println(a + b));
        response.whenComplete((res, err) -> System.out.println("result: " + res + "\nerror: " + err));
        String result = response.get(1, TimeUnit.SECONDS);
        assertTrue(result.startsWith("hi dude"));
    }
}