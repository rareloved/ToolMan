package com.andy.test.initsql;

import org.junit.Test;

import java.io.*;

/**
 * Created by zhangshouzheng on 2016/10/20.
 */
public class InitSqlTest {
    @Test
    public void testSql(){
        try {
            OutputStream outputStream = new FileOutputStream("src/fixdata.sql");
            int size = 128;
            StringBuffer buffer = new StringBuffer();
            for(int i=0;i<size;i++){
                String tableNo= "";
                String stri = i+"";
                if(stri.toCharArray().length ==1){
                    tableNo = "00"+i;
                }else if(stri.toCharArray().length ==2){
                    tableNo = "0"+i;
                }else if(stri.toCharArray().length ==3){
                    tableNo = ""+i;
                }
                String temp ="delete from reconciliation_result_"+tableNo+"  where id in (select id from (select  max(id) as id,order_id,count(order_Id) as ct from reconciliation_result_"+tableNo+" group by order_Id having ct >1 order by ct desc) as tab);";
//                String temp = "select top 1 id from reconciliation_result_"+tableNo+" where order_id in (SELECT order_id FROM reconciliation_result_"+tableNo+" GROUP BY order_id HAVING count(1) >1)";
                buffer.append(temp);
                buffer.append("\n");

            }

            for(int i=0;i<size;i++){
                String tableNo= "";
                String stri = i+"";
                if(stri.toCharArray().length ==1){
                    tableNo = "00"+i;
                }else if(stri.toCharArray().length ==2){
                    tableNo = "0"+i;
                }else if(stri.toCharArray().length ==3){
                    tableNo = ""+i;
                }
                String temp ="CREATE UNIQUE INDEX order_result_unique ON reconciliation_result_"+tableNo+"(order_id);";
//                String temp = "select top 1 id from reconciliation_result_"+tableNo+" where order_id in (SELECT order_id FROM reconciliation_result_"+tableNo+" GROUP BY order_id HAVING count(1) >1)";
                buffer.append(temp);
                buffer.append("\n");

            }
            System.out.println(buffer);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("hash:"+"D20161019101449160003128379".hashCode()%128);
    }
}