package com.andy.test;

import com.le.Children;
import org.junit.Test;

/**
 * <p>DESCRIPTION:  类描述
 * <p>CALLED BY:   zhangshouzheng
 * <p>UPDATE BY:   zhangshouzheng
 * <p>CREATE DATE: 2016/12/13
 * <p>UPDATE DATE: 2016/12/13
 *
 * @version 1.0
 * @since java 1.7.0
 */
public class TestAndy {
    @Test
    public void testAndy(){
        Children children = new Children();
//        children.setName("sss");
        System.out.println(children.getName());
    }
}
