package mangri.sample.jaxrs;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SampleApiIT {
    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample-jaxrs.war").addPackage(SampleApi.class.getPackage());
    }

    @Test
    public void callApi() {
        Client client = ClientBuilder.newClient();
        String response = client.target("http://127.0.0.1:8080/sample-jaxrs/api/check").queryParam("name", "dude")
                .request(MediaType.TEXT_PLAIN).get(String.class);
        assertEquals("it works dude", response);
    }
}