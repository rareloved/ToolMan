package com.andy.tool;

import com.andy.tool.impl.ByPlaneTravelServiceImpl;

/**
 * Created by zhangshouzheng on 2016/8/2.
 */
public class BlankMan extends Person {

    public BlankMan() {
        super(new ByPlaneTravelServiceImpl());
    }

    @Override
    public void show() {
        System.out.println("黑人就是喜欢坐bus");
    }
}
