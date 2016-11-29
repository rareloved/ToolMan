package com.andy.test.math;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by zhangshouzheng on 2016/10/26.
 */
public class MathEqualTest {
    @Test
    public void testBigDecimalLong(){
        BigDecimal bigDecimal =new BigDecimal("100");
        Long longVal = 100l;
        if(longVal.equals(bigDecimal.longValue())){
            System.out.println("两个数据相等:"+bigDecimal.longValue());
        }else {
            System.out.println("sorry，两个数据不相等"+bigDecimal);
        }
        boolean flag = true && false || false;
        System.out.printf("flag:"+flag);

    }
    @Test
    public void testBigDecimalLong2(){
        Byte b = new Byte("100");
        Byte longVal = 100;
        if(longVal == b){
            System.out.println("两个数据相等:"+b.longValue());
        }else {
            System.out.println("sorry，两个数据不相等"+b);
        }
    }
}
