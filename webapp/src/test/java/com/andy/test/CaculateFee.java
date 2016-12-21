package com.andy.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * <p>DESCRIPTION:  类描述
 * <p>CALLED BY:   zhangshouzheng
 * <p>UPDATE BY:   zhangshouzheng
 * <p>CREATE DATE: 2016/12/5
 * <p>UPDATE DATE: 2016/12/5
 *
 * @version 1.0
 * @since java 1.7.0
 */
public class CaculateFee {
    @Test
    public void testCaculateFee(){
        BigDecimal currentVolumebd = new BigDecimal("15551905497");
        BigDecimal ratebd = new BigDecimal("0.5");//
        BigDecimal rateRet = currentVolumebd.multiply(ratebd).divide(new BigDecimal("3650000"),2,BigDecimal.ROUND_DOWN);
        double   doubleValue   =   rateRet.setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
        Long longValue = new BigDecimal(doubleValue+"").multiply(new BigDecimal(100+"")).longValue();

        //设置应收服务费 这里long传递分，序列化实现分转元
//        platformConfirmServiceExpense.setRecServiceExpense(new BigDecimal(doubleValue+"").multiply(new BigDecimal(100+"")).longValue());
        System.out.println("计算活期每日应收服务费，doubleValue="+doubleValue+",rateRet="+rateRet+",longValue="+longValue);
    }

    @Test
    public void testFee(){
        double a = 175926782.25;
        double b = 175885018.17;
        BigDecimal ret = new BigDecimal(a+"").subtract(new BigDecimal(b+""));
        System.out.println("ret:"+ret);
    }
}
