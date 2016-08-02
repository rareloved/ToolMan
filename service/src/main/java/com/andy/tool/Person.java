package com.andy.tool;

/**
 * Created by zhangshouzheng on 2016/8/2.
 */
public abstract class Person {
    private TravelStrategyService travelStrategyService;
    Person(TravelStrategyService travelStrategyService){
        this.travelStrategyService =travelStrategyService;
    }

    /**
     * 人具有表演能力
     */
    public abstract void show();

    /**
     * 旅游
     */
    public void travel(){
        this.travelStrategyService.travel();
    }
}
