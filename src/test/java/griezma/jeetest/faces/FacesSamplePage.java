package griezma.jeetest.faces;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("faces-sample.xhtml")
public class FacesSamplePage {

    @FindBy(css = ".greeting")
    private WebElement greeting;

    @FindBy(css = "table")
    private WebElement broTable;

    @FindBy(css = "input[name$=newName]")
    private WebElement nameInput;

    @FindBy(css = "input[name$=newMessage]")
    private WebElement messageInput;

    @FindBy(css = "input[type=submit]")
    private WebElement submitButton;

    String getGreeting() {
        return greeting.getText();
    }

    FacesSamplePage addGreeting(String name, String message) {
        nameInput.sendKeys(name);
        messageInput.sendKeys(message);
        guardHttp(submitButton).click();
        return this;
    }

    WebElement broTable() {
        return broTable;
    }
}
