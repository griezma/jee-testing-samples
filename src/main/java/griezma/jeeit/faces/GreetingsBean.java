package griezma.jeeit.faces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Named("greet")
@SessionScoped
public class GreetingsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    private String greeting = "Hi there";

    public List<BroHello> getBroHellos() {
        return List.of(
            new BroHello("Mani", "Seavas"), 
            new BroHello("Susi", "Griassdi"));
    }

    @AllArgsConstructor
    public static class BroHello {
        @Getter
        String name, message;
    }
}
