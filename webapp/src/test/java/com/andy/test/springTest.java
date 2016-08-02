package com.andy.test;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by zhangshouzheng on 2016/7/28.
 */
public class springTest {

    @Test
    public void getWhatIsUp(){
        //测试，请使用断言
        /**
         * 判断某页数据
         *
         * 前多少条数据是上一个sheet页，多少条是下一个sheet页。
         *
         *
         */

//        HashMap map = java.com.andy.test.UtilBean.resolveSheetNoSuperT(2,7,10,10);
//        System.out.println("第"+2+"页数据，filterCount:"+map.get("filterCount")+"||preSheetNo:"+map.get("preSheetNo")+"||nextSheetNo:"+map.get("nextSheetNo"));
        int i=1;
      for(;i<20;i++){
            HashMap map = UtilBean.resolveSheetNoSuper(i,7,100,10);
            System.out.println("第"+i+"页数据，filterCount:"+map.get("filterCount")+"||preSheetNo:"+map.get("preSheetNo")+"||nextSheetNo:"+map.get("nextSheetNo"));
        }
    }
}
