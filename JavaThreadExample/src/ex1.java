import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ex1 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);

        service.submit(() -> {
            System.out.println("service " + Thread.currentThread());
        });
        System.out.println("main " + Thread.currentThread());

        Thread.sleep(1000);
        service.submit(() -> {
            System.out.println("service " + Thread.currentThread());
        });
        System.out.println("main " + Thread.currentThread());
    }
}
