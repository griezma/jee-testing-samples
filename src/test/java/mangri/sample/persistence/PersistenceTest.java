package mangri.sample.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PersistenceTest {
    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war").addPackage(NumberService.class.getPackage())
                .addAsResource("persistence/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("persistence/import.sql", "import.sql")
                .addAsWebInfResource("persistence/sample-ds.xml", "sample-ds.xml")
                .addAsWebInfResource("persistence/web.xml", "web.xml");
    }

    @Inject
    NumberService numbers;

    @PersistenceContext
    EntityManager em;

    @Resource(lookup = "java:jboss/jdbc/SampleDS", name = "jdbc/SampleDS")
    DataSource sampleDs;

    @Resource(name="fibo")
    Integer otherRef;

    @Test
    public void testResourceInjection() throws NamingException {
        InitialContext naming = new InitialContext();
        System.out.println("naming: " + naming.lookup("java:comp/env/jdbc/SampleDS"));
        Iterator<NameClassPair> it = naming.list("java:comp/env").asIterator();
        Stream.iterate(it, Iterator::hasNext, UnaryOperator.identity())
            .map(Iterator::next)
            .map(p -> p.getName())
            .forEach(System.out::println);
    }

    @Test
    public void canInjectDataSource() {
        assertNotNull(sampleDs);
    }

    @Test
    public void canInjectEntityManager() {
        Fibonacci fibo = em.find(Fibonacci.class, 0);
        assertNotNull(fibo);
    }

    @Test
    public void shouldReturnCachedFibo() {
        assertEquals(0, (long)numbers.fibo(0));
    }
}
