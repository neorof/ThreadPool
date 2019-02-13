import java.util.concurrent.*;

/**
 * @Author: modishou
 * @Date: 2019/1/13 19:11
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            return "hello";
        });
        executor.shutdown();
        System.out.println("sdf");
    }
}
