package com.andy.test;

import java.util.HashMap;

/**
 * Created by zhangshouzheng on 2016/7/29.
 */
public class UtilBean {
    /**
     * 获取数据所处的sheetNo号
     * @param
     * @param
     * @return
     */
    public static int resolveSheetNo(int getCurrentPageNo,int getPageSize,int perSheetSize,int maxSheetSize) {
        int sheetNo = 1;
        int insertedData= getCurrentPageNo*getPageSize;
        sheetNo = insertedData%perSheetSize>0?(1+insertedData/perSheetSize):(insertedData/perSheetSize);
        if (sheetNo == 0){
            return -1;
        }
        if(sheetNo%maxSheetSize == 0){//整除
            sheetNo = 10;
        }else{
            sheetNo = sheetNo%maxSheetSize;
        }
        return sheetNo;
    }

    public static HashMap resolveSheetNoSuper(int getCurrentPageNo, int getPageSize, int perSheetSize, int maxSheetSize) {
        int sheetNo = 1;
        int insertedData= getCurrentPageNo*getPageSize;
        //第1页，每页7条 7   1-7
        //第2页，每页7条 14   8-14
        //第3页，每页7条 21   15-21
        //第4页，每页7条 28   22-28
        //第5页，每页7条 35   29-35
        //43-49
        //判断当前页数据是否跨sheet页？1 10 20 30 40 50 60 70 80 90 100 110 ……单excel 10个sheet
        // (28- 28/10*10) < 0
        HashMap map =new HashMap<String,Integer>();
        int x =(insertedData/perSheetSize)*perSheetSize;
        int startPos=insertedData-getPageSize+1;
        int endPos = insertedData;

        int sheetNoT = insertedData%perSheetSize>0?(1+insertedData/perSheetSize):(insertedData/perSheetSize);
        if(endPos>x&&startPos<=x){
            map.put("nextSheetNo",sheetNoT);
            map.put("preSheetNo",sheetNoT-1);
            //前n条数据在上一个sheet页
            map.put("filterCount",x-startPos+1);
        }else{
            map.put("nextSheetNo",null);
            map.put("preSheetNo",sheetNoT);
            //前n条数据在上一个sheet页
            map.put("filterCount",getPageSize);
        }
        return map;
    }
}
