package mangri.sample.servlet;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SampleServletIT {
    @Deployment(testable = false)
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war")
            .addClass(SampleServlet.class);
    }

    @Test
    public void servletShouldRespondWithMessage(@ArquillianResource URL contextUrl) throws Exception {
        URL url = new URL(contextUrl, "check");
        try (InputStream is = url.openStream()) {
            String response = new BufferedReader(new InputStreamReader(is)).readLine();
            assertEquals("yes it works", response);
        }
    }

}
