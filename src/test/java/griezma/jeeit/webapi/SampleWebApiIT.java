package griezma.jeeit.webapi;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SampleWebApiIT {
    @Deployment(testable = false)
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war").addPackage(SampleWebApi.class.getPackage());
    }

    @Test
    public void remoteCallToCheckShouldRespondAsExpected(@ArquillianResource URL contextUrl) throws Exception {
        Client client = ClientBuilder.newClient();
        String response = client.target(new URL(contextUrl, "api/check").toURI())
        .queryParam("name", "dude")
                .request(MediaType.TEXT_PLAIN).get(String.class);
        assertEquals("it works dude", response);
    }
}