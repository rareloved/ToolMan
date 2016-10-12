package com.andy.test;

import org.junit.Test;

/**
 * 移位运算符<<   >>
 * Created by zhangshouzheng on 16-9-29.
 */
public class HandlerTest {
    @Test
    public void testHandler(){
//        00001010
//        System.out.println("ret:"+(1<<3));
        for(int i = 0;i<100; i++ ) {
            final int pageStart = (i * 100);
            System.out.println("pageStart:"+pageStart);
        }
    }
}
