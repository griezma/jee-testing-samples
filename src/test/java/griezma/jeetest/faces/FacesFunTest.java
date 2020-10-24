package griezma.jeetest.faces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@RunWith(Arquillian.class)
public class FacesFunTest {
    @Deployment(testable = false)
    public static WebArchive deploy() throws Exception {
        File webapp = new File("src/main/webapp");

        WebArchive war = ShrinkWrap.create(WebArchive.class, "sample.war")
                .addPackage(GreetingsBean.class.getPackage())
                .addAsWebInfResource(new File(webapp, "WEB-INF/web.xml"), "web.xml")
                .addAsWebResource(new File(webapp, "faces-sample.xhtml"), "faces-sample.xhtml");

        addDirectory(webapp, "WEB-INF/templates", war::addAsWebResource);
        addDirectory(webapp, "resources/styles", war::addAsWebResource);

        return war;
    }

    static void addDirectory(File root, String directory, BiConsumer<File, String> addToArchive) throws IOException {
        Path rootPath = root.toPath();
        Path dirPath = rootPath.resolve(Paths.get(directory));
        Files.list(dirPath)
            .forEach(p -> addToArchive.accept(p.toFile(), rootPath.relativize(p).toString()));
    }

    @Drone
    WebDriver browser;

    @Page
    SamplePage samplePage;

    @ArquillianResource
    URL context;


    @Before
    public void initialPage() {
        Graphene.goTo(SamplePage.class);
    }

    @Test
    public void loadingDefaultPageOfContext() throws MalformedURLException {
        browser.get(context.toString());
        assertEquals("Sample Facelet Page", browser.getTitle());
    }

    @Test
    public void checkHtmlResponse(@ArquillianResource URL context) throws Exception {
        String response = Http.GET(new URL(context, "faces-sample.xhtml"));
        //System.out.println(response);
        assertTrue(response.contains("Hi there"));
    }

    @Test
    public void validateSamplePage() {
        assertEquals("Hi there", samplePage.getGreeting());
    }
}