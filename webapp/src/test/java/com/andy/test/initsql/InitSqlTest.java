package com.andy.test.initsql;

import org.junit.Test;

import java.io.*;

/**
 * Created by zhangshouzheng on 2016/10/20.
 */
public class InitSqlTest {
    @Test
    public void testSql() {
        try {
            OutputStream outputStream = new FileOutputStream("src/fixdata.sql");
            int size = 128;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < size; i++) {
                String tableNo = "";
                String stri = i + "";
                if (stri.toCharArray().length == 1) {
                    tableNo = "00" + i;
                } else if (stri.toCharArray().length == 2) {
                    tableNo = "0" + i;
                } else if (stri.toCharArray().length == 3) {
                    tableNo = "" + i;
                }
                String temp = "delete from reconciliation_result_" + tableNo + "  where id in (select id from (select  max(id) as id,order_id,count(order_Id) as ct from reconciliation_result_" + tableNo + " group by order_Id having ct >1 order by ct desc) as tab);";
//                String temp = "select top 1 id from reconciliation_result_"+tableNo+" where order_id in (SELECT order_id FROM reconciliation_result_"+tableNo+" GROUP BY order_id HAVING count(1) >1)";
                buffer.append(temp);
                buffer.append("\n");

            }

            for (int i = 0; i < size; i++) {
                String tableNo = "";
                String stri = i + "";
                if (stri.toCharArray().length == 1) {
                    tableNo = "00" + i;
                } else if (stri.toCharArray().length == 2) {
                    tableNo = "0" + i;
                } else if (stri.toCharArray().length == 3) {
                    tableNo = "" + i;
                }
                String temp = "CREATE UNIQUE INDEX order_result_unique ON reconciliation_result_" + tableNo + "(order_id);";
//                String temp = "select top 1 id from reconciliation_result_"+tableNo+" where order_id in (SELECT order_id FROM reconciliation_result_"+tableNo+" GROUP BY order_id HAVING count(1) >1)";
                buffer.append(temp);
                buffer.append("\n");

            }
            System.out.println(buffer);
            outputStream.write(buffer.toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("hash:"+"D20161019101449160003128379".hashCode()%128);
    }

    @Test
    public void testSql1() {
        int size = 128;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            String tableNo = "";
            String stri = i + "";
            if (stri.toCharArray().length == 1) {
                tableNo = "00" + i;
            } else if (stri.toCharArray().length == 2) {
                tableNo = "0" + i;
            } else if (stri.toCharArray().length == 3) {
                tableNo = "" + i;
            }
            String temp = " SELECT order_id,count(1) FROM reconciliation_result_" + tableNo + " GROUP BY order_id HAVING count(1) >1 union ";
//                String temp = "select top 1 id from reconciliation_result_"+tableNo+" where order_id in (SELECT order_id FROM reconciliation_result_"+tableNo+" GROUP BY order_id HAVING count(1) >1)";
            buffer.append(temp);
            buffer.append("\n");
        }
        System.out.println(buffer);
    }

    /**
     * track error data
     * @param
     * @return
     * @author zhangshouzheng
     * @date 2016/11/3 14:30
     */
    @Test
    public void testSql2() {
        int size = 128;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            String tableNo = "";
            String stri = i + "";
            if (stri.toCharArray().length == 1) {
                tableNo = "00" + i;
            } else if (stri.toCharArray().length == 2) {
                tableNo = "0" + i;
            } else if (stri.toCharArray().length == 3) {
                tableNo = "" + i;
            }
//            SELECT *,'000' AS tabname FROM `send_temp_data_his_000` WHERE create_date > '2016-10-29' and create_date < '2016-10-30' and temp_data like '%JTKHLX%';
            String temp = "SELECT *,'"+tableNo+"' AS tabname FROM send_temp_data_his_"+tableNo +" WHERE create_date >= '2016-10-29' and create_date <= '2016-10-30' and temp_data like '%JTKHLX%' union ";
//                String temp = "select top 1 id from reconciliation_result_"+tableNo+" where order_id in (SELECT order_id FROM reconciliation_result_"+tableNo+" GROUP BY order_id HAVING count(1) >1)";
            buffer.append(temp);
            buffer.append("\n");
        }
        System.out.println(buffer);
    }

    /**
     *
     * ALTER TABLE `reconciliation_result_0`
     DROP INDEX `lx_order_id` ,
     ADD UNIQUE INDEX `lx_order_id` (`order_id`, `fund_channel_code`) USING BTREE ;
     */
    @Test
    public  void multiChannelIndexSql() throws IOException {
        OutputStream outputStream = new FileOutputStream("src/multiChannel.sql");
        StringBuffer sqlTotal = new StringBuffer();
        for(int i = 0 ;i<128;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%03d",i));
            sql.append(" DROP INDEX lx_order_id ,ADD UNIQUE INDEX lx_order_id (order_id, fund_channel_code) USING BTREE ;");
            sqlTotal.append(sql +"\n");
            System.out.println(sql.toString());
        }
        outputStream.write(sqlTotal.toString().getBytes());
        outputStream.close();

    }

    /**
     *
     * ALTER TABLE `business_reconciliation_his_000`
     MODIFY COLUMN `bank_card_no`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号' AFTER `trade_status`;
     */
    @Test
    public  void moneyBoxBankNoLength() throws IOException {
        OutputStream outputStream = new FileOutputStream("src/moneyBoxBankNoLength.sql");
        StringBuffer sqlTotal = new StringBuffer();
        for(int i = 0 ;i<128;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE business_reconciliation_his_");
            sql.append(String.format("%03d",i));
            sql.append(" MODIFY COLUMN bank_card_no  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号' AFTER trade_status;");
            sqlTotal.append(sql +"\n");
            System.out.println(sql.toString());
        }
        outputStream.write(sqlTotal.toString().getBytes());
        outputStream.close();

    }

    @Test
    public  void multiChannelIndexSqlTestEnv1(){
        for(int i = 0 ;i<2;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%01d",i));
            sql.append(" DROP INDEX lx_order_id ,ADD UNIQUE INDEX lx_order_id (order_id, fund_channel_code) USING BTREE ;");
            System.out.println(sql.toString());
        }
    }

    @Test
    public  void multiChannelIndexSqlTestEnv2(){
        for(int i = 0 ;i<2;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%01d",i));
            sql.append(" DROP INDEX lx_order_id ,ADD UNIQUE INDEX lx_order_id (order_id) USING BTREE ;");
            System.out.println(sql.toString());
        }
    }
    @Test
    public void testddd() {
        System.out.println("hash:" + "1612220045194096120326".hashCode() % 128);
        Integer a = null;
        if(a !=null && a ==9){
            System.out.println(a);
        }
    }
}
