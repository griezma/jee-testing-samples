package griezma.jeetest.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import griezma.jeetest.persistence.FibonacciService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class PersistenceTest {
    @Deployment
    public static WebArchive deploy() {
        return ShrinkWrap.create(WebArchive.class, "sample.war").addPackage(FibonacciService.class.getPackage())
                .addAsResource("persistence/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("persistence/import.sql", "import.sql")
                .addAsWebInfResource("persistence/sample-ds.xml", "sample-ds.xml")
                .addAsWebInfResource("persistence/web.xml", "web.xml");
                //.addAsWebInfResource("logging.properties");
    }

    @Inject
    FibonacciService fibos;

    @PersistenceContext
    EntityManager em;

    @Resource(lookup = "java:global/jdbc/SampleDS")
    DataSource sampleDs;

    @Test
    public void canInjectAndUseDataSource() throws SQLException {
        assertNotNull(sampleDs);
        try (Connection conn = sampleDs.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("select count(*) from fibos");
            assertTrue(rs.next());
            System.out.println("count: " + rs.getInt(1));
            assertEquals(3, rs.getInt(1));
        }
    }

    @Test
    public void shouldFetchCalculatedFibosAndFromCache() {
        int base = 23;
        BigInteger fibo = fibos.fibo(base);
        System.out.println("fibo of " + base + " is " + fibo);
        assertTrue("big positive expected: " + fibo, fibo.compareTo(BigInteger.ZERO) > 0);

        fibos.fibo(22);
        fibos.fibo(25);
    }
}
