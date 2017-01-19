package com.le;

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
public class Children extends AndyFather {
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
