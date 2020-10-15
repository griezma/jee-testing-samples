package mangri.sample.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@RequestScoped
public class OrderService {
    @Inject
    Event<String> orderEvent;
    
    public void createOrder(String product, int amount) {
        orderEvent.fire(product + ", " + amount);
    }
}
