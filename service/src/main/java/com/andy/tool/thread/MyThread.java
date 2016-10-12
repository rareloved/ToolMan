package com.andy.tool.thread;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Created by zhangshouzheng on 2016/9/22.
 */
public class MyThread implements Callable {
    private String taskNum;

    MyThread(String taskNum) {
        this.taskNum = taskNum;
    }
    @Override
    public Object call() throws Exception {
        System.out.println(">>>" + taskNum + "任务启动");
        Date dateTmp1 = new Date();
        Thread.sleep(1000);
        Date dateTmp2 = new Date();
        long time = dateTmp2.getTime() - dateTmp1.getTime();
        System.out.println(">>>" + taskNum + "任务终止");
        return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}
