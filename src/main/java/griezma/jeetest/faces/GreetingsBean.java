package griezma.jeetest.faces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Named("greet")
@RequestScoped
public class GreetingsBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    private String greeting = "Hi there";

    @Inject
    private Collection<BroHello> broDb;

    @Getter @Setter
    private String newName, newMessage;

    @PostConstruct
    private void created() {
        log.info("*** created " + this);
    }

    public void addGreeting() {
        log.info("*** addGreeting");
        broDb.add(new BroHello(newName, newMessage));
        //return "faces-sample.xhtml?faces-redirect=true";

        newName = null;
        newMessage = null;
    }

    @Produces @SessionScoped @Named
    public Collection<BroHello> getBroHellos() {
        log.info("*** createBroHellos");
        return new ArrayList<BroHello>(List.of(
            new BroHello("Mani", "Seavas"), 
            new BroHello("Susi", "Griassdi")
        ));
    }

    @Data
    public static class BroHello implements Comparable<BroHello> {
        private static AtomicInteger s_keyGen = new AtomicInteger();

        private final int id;

        @NotNull
        private String name, message;

        BroHello(String name, String message) {
            this.id = s_keyGen.incrementAndGet();
            this.name = name;
            this.message = message;
        }

        @Override
        public int compareTo(BroHello other) {
            log.info("*** compareTo " + this + ", " + other + ", " + (name != null) + ", other " + (other != null));
            return name.compareTo(other.name);
        }
    }
}
