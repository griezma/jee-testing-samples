package griezma.jeetest.faces;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("faces-sample.xhtml")
public class SamplePage {
    @Drone
    private WebDriver browser;

    @FindBy(css = ".greeting")
    private WebElement greeting;

    @FindBy(css = "table")
    private WebElement broTable;

    public String getGreeting() {
        return greeting.getText();
    }
}
