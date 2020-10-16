package mangri.sample.jsf;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.extern.java.Log;

@Log
@RunWith(Arquillian.class)
public class JsfSampleTest {
    @Deployment
    public static WebArchive deploy() {
        File webApp = new File("src/main/webapp");

        return ShrinkWrap.create(WebArchive.class, "sample.war")
            .addPackage(GreetController.class.getPackage())
            .addAsWebInfResource(new File(webApp, "WEB-INF/web.xml"), "web.xml")
            .addAsWebResource(new File(webApp, "jsf/sample.xhtml"), "jsf/sample.xhtml");
    }

    @Test
    public void shouldDeliverHtml(@ArquillianResource URL context) throws Exception {
        String response = Http.GET(new URL(context, "jsf/sample.xhtml"));
        log.info(response);
        assertTrue(response.contains("Hi there"));
    }
}