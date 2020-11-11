package week4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockConditionTest {

    private volatile Integer value = null;
    private Lock lock = new ReentrantLock();
    private Condition calComplete = lock.newCondition();

    public void sum(int num) {
        lock.lock();
        try {
            value = fibo(num);
            calComplete.signal();
        } finally {
            lock.unlock();
        }
    }

    private int fibo(int a) {
        return a;
    }

    public int getValue() throws InterruptedException {
        lock.lock();
        try {
            while (value == null) {
                calComplete.await();
            }
        } finally {
            lock.unlock();
        }
        return value;
    }

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        final LockConditionTest method = new LockConditionTest();
        Thread thread = new Thread(() -> {
            method.sum(100);
        });
        thread.start();
        thread.join();
        int result = method.getValue(); //这是得到的返回值
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}