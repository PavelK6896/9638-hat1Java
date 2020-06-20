import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ex3 {
    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("tow thread");
            return thread;
        });

        executorService.submit(() -> {
            System.out.println("service" + Thread.currentThread());
        });
        System.out.println("main " + Thread.currentThread());
        Thread.sleep(1000);
        executorService.submit(() -> {
            System.out.println("service" + Thread.currentThread());
        });
        System.out.println("main " + Thread.currentThread());
    }
}
