package mangri.sample.persistence;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class NumberService {
    @PersistenceContext
    EntityManager numberCache;

    public int fibo(int number) {
        return Optional.ofNullable(numberCache.find(Fibonacci.class, number))
            .map(f -> f.getFibo())
            .orElse(-1);
    }
}
