package com.andy.tool.impl;

import com.andy.tool.TravelStrategyService;

/**
 * Created by zhangshouzheng on 2016/8/2.
 */
public class ByPlaneTravelServiceImpl implements TravelStrategyService {
    @Override
    public void travel() {
        System.out.println("this is by plane and by plane can see the blue sky.");
    }
}
