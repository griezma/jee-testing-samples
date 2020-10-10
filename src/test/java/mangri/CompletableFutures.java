package mangri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class CompletableFutures {
    @Test
    public void test() {
        CompletableFuture<Number> p = CompletableFuture.supplyAsync(() -> 42);
        sleep(100);
        assertFalse(p.isDone());
        assertEquals(42, p.join());
        assertTrue(p.isDone());
    }

    @Test
    public void test2() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Integer> p = CompletableFuture.supplyAsync(CompletableFutures::supply);
        assertFalse(p.isDone());
        p = p.thenApplyAsync(CompletableFutures::process);
        assertFalse(p.isDone());
        //p.completeAsync(CompletableFutures::supply);
        System.out.println("about to wait");;
        assertEquals((Integer)2, p.get(5, TimeUnit.SECONDS));
        System.out.println("done");
    }
    
    private static int supply() {
        sleep(500);
        return 1;
    }

    private static int process(int value) {
        sleep(500);
        return value + 1;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
