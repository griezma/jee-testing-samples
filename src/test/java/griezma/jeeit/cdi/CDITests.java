package griezma.jeeit.cdi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.Getter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import griezma.jeeit.cdi.OrderService;


@RunWith(Arquillian.class)
public class CDITests {
    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war")
            .addPackage(OrderService.class.getPackage());
    }

    @RequestScoped
    public static class Observer {
        @Getter
        private List<String> createdOrders = new ArrayList<>();

        public void onOrderCreated(@Observes String orderCreated) {
            createdOrders.add(orderCreated);
        }
    }

    @Inject
    OrderService orderService;

    @Inject
    Observer observer;

    @Test
    public void createOrderShouldTriggerEvent() {
        orderService.createOrder("apple", 5);
        assertEquals(1, observer.getCreatedOrders().size());
    }
}