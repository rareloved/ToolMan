package com.andy.test.datatype;

import org.junit.Test;

/**
 * <p>DESCRIPTION:  类描述
 * <p>CALLED BY:   zhangshouzheng
 * <p>UPDATE BY:   zhangshouzheng
 * <p>CREATE DATE: 2017/2/28
 * <p>UPDATE DATE: 2017/2/28
 *
 * @version 1.0
 * @since java 1.7.0
 */
public class ByteEqualsTest {
    @Test
    public void testHandler(){
        Byte a = new Byte("127");
        Byte b = new Byte("127");
        System.out.println(!b.equals(a));
    }
}
