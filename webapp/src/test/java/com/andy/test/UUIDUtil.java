package com.andy.test;

import java.util.UUID;

/**
 * <p>Title:</p>
 * <p>Descriprion:</p>
 * <p>Company乐视控股（北京）有限公司</p>
 *
 * @author zhoushiqi
 * @date 2016/6/13.
 */
public class UUIDUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-","").substring(0,30);
    }

    public static void main(String[] args) {
        for(int i =0;i<363;i++) {
            System.out.println(UUIDUtil.getUUID());
        }
    }

}
