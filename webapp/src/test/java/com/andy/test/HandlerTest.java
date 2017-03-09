package com.andy.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * 移位运算符<<   >>
 * Created by zhangshouzheng on 16-9-29.
 */
public class HandlerTest {
    @Test
    public void testHandler(){
//        00001010
//        System.out.println("ret:"+(1<<3));
//        for(int i = 0;i<100; i++ ) {
//            final int pageStart = (i * 100);
//            System.out.println("pageStart:"+pageStart);
//        }
//        System.out.println("hash:"+"D20170117142802160011849479".hashCode()%128);
        BigDecimal sumMoney = new BigDecimal("0");
        if(sumMoney != null && sumMoney.compareTo(new BigDecimal("0")) > 0) {
            System.out.println("get in it");
        }
    }
}
