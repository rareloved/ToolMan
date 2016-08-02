package com.andy.tool.impl;

import com.andy.tool.TravelStrategyService;

/**
 * Created by zhangshouzheng on 2016/8/2.
 */
public class ByBusTravelServiceImpl implements TravelStrategyService {
    @Override
    public void travel() {
        System.out.println("travel by bus can meet many girls.");
    }
}
