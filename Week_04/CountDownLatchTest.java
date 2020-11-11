package week4;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    private volatile Integer value = null;
    private CountDownLatch latch;

    public void sum(int num) {
        value = fibo(num);
        latch.countDown();
    }

    private int fibo(int a) {
        return a;
    }

    public int getValue() throws InterruptedException {
        latch.await();
        return value;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);
        final CountDownLatchTest method = new CountDownLatchTest();
        method.setLatch(latch);
        Thread thread = new Thread(() -> {
            method.sum(100);
        });
        thread.start();

        int result = method.getValue();
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}