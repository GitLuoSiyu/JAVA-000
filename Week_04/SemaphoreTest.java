package week4;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private Integer value = null;
    private final Semaphore semaphore = new Semaphore(1);

    public void sum(int num) throws InterruptedException {
        semaphore.acquire();
        value = fibo(num);
        System.out.println(value);
        semaphore.release();
    }

    private int fibo(int a) {
        return a;
    }

    public int getValue() throws InterruptedException {
        int result;
        semaphore.acquire();
        result = this.value;
        semaphore.release();
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        final SemaphoreTest method = new SemaphoreTest();
        Thread thread = new Thread(() -> {
            try {
                method.sum(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        int result = method.getValue(); //这是得到的返回值
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}