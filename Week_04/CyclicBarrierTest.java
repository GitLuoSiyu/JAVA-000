package week4;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    private volatile Integer value = null;
    CyclicBarrier barrier;

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public void sum(int num) throws BrokenBarrierException, InterruptedException {
        value = fibo(num);
        barrier.await();
    }

    private int fibo(int a) {
        return a;
    }

    public int getValue() throws InterruptedException {
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();

        final CyclicBarrierTest method = new CyclicBarrierTest();
        CyclicBarrier barrier = new CyclicBarrier(1, ()-> {
            int result = 0;
            try {
                result = method.getValue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("异步计算结果为："+result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        });
        method.setBarrier(barrier);

        Thread thread = new Thread(() -> {
            try {
                method.sum(100);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}