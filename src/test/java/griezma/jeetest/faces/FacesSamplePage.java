package griezma.jeetest.faces;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

import java.util.List;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

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

    @FindBy(css = ".fo_mess--invalid")
    private List<WebElement> validation;

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

    String validationMessage() {
        if (validation.isEmpty()) {
            return "";
        }
        return validation.get(0).getText();
    }
}
