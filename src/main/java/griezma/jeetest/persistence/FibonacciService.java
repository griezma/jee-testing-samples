package griezma.jeetest.persistence;

import java.math.BigInteger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class FibonacciService {
    @PersistenceContext
    EntityManager fiboCache;

    public BigInteger fibo(int number) {

        var fiboFromCache = fiboCache.createNamedQuery("fibo", BigInteger.class)
            .setParameter("base", number)
            .getResultStream()
            .findAny();

        return fiboFromCache.orElseGet(() -> calculateFibo(number));
    }

    private BigInteger calculateFibo(int number) {
        BigInteger numberAsBig = BigInteger.valueOf(number);

        var cachedSmallerFibo = fiboCache.createNamedQuery("fibo", BigInteger.class)
            .setParameter("base", number - 1)
            .getResultStream()
            .findAny();

        var result = cachedSmallerFibo
            .map(smallerFibo -> smallerFibo.multiply(numberAsBig))
            .orElseGet(() -> calculateFibo(number - 1)
            .multiply(numberAsBig));

        fiboCache.persist(new CachedFibo(number, result));
        return result;
    }
}
