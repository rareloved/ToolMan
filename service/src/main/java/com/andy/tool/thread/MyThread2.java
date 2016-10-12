package com.andy.tool.thread;

/**
 * Created by zhangshouzheng on 2016/9/22.
 */
public class MyThread2 implements Runnable {
    private int i;
    MyThread2(int i){
        this.i=i;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread启动线程"+i);
    }
}
