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
//        OutputStream outputStream = new FileOutputStream("src/multiChannel.sql");
        StringBuffer sqlTotal = new StringBuffer();
        for(int i = 0 ;i<128;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%03d",i));
            sql.append(" ADD COLUMN channel_type  varchar(64) NULL COMMENT '渠道类型 UPS MONEYBOX' AFTER inst_order_no;");
            sqlTotal.append(sql +"\n");
            System.out.println(sql.toString());
        }
        System.out.println(sqlTotal.toString());
//        outputStream.write(sqlTotal.toString().getBytes());
//        outputStream.close();

    }

    @Test
    public  void multiChannelIndexSql22() throws IOException {
//        OutputStream outputStream = new FileOutputStream("src/multiChannel.sql");
        StringBuffer sqlTotal = new StringBuffer();
        for(int i = 0 ;i<128;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%03d",i));
            sql.append(" ADD COLUMN channel_type  varchar(64) NULL COMMENT '渠道类型 UPS MONEYBOX' AFTER inst_order_no,ADD COLUMN rec_business_ordertype  int(8) NULL COMMENT '对账订单业务类型'  AFTER channel_type;");
            sqlTotal.append(sql +"\n");
            System.out.println(sql.toString());
        }
        System.out.println(sqlTotal.toString());
//        outputStream.write(sqlTotal.toString().getBytes());
//        outputStream.close();

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
            StringBuffer sql = new StringBuffer("ALTER TABLE order_reconciliation_redundancy_his_");
            sql.append(String.format("%03d",i));
            sql.append(" MODIFY COLUMN pay_bank_cardno  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付银行卡号' AFTER trade_initial_state;");
            sqlTotal.append(sql +"\n");
            System.out.println(sql.toString());
        }
        outputStream.write(sqlTotal.toString().getBytes());
        outputStream.close();

    }

    @Test
    public  void multiChannelIndexSqlTestEnv1(){
        for(int i = 0 ;i<128;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%03d",i));
            sql.append(" DROP INDEX order_result_unique ,ADD UNIQUE INDEX order_result_unique (order_id, fund_channel_code) USING BTREE ;");
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
        System.out.println("hash:" + "1701160012927087145007".hashCode() % 128);
        Integer a = null;
        if(a !=null && a ==9){
            System.out.println(a);
        }
    }


    /**
     * ALTER TABLE `order_reconciliation_redundancy`
     ADD COLUMN `letv_user_id`  bigint(20) NULL COMMENT '用户id' AFTER `telephone`;
     * 订单表、
     * @throws IOException
     */
    @Test
    public  void orderHistory() throws IOException {
//        OutputStream outputStream = new FileOutputStream("src/multiChannel.sql");
        StringBuffer sqlTotal = new StringBuffer();
        for(int i = 0 ;i<128;i++){
            StringBuffer sql = new StringBuffer("ALTER TABLE reconciliation_result_");
            sql.append(String.format("%03d",i));
            sql.append(" ADD COLUMN letv_user_id  bigint(20) NULL COMMENT '用户id' AFTER telephone;");
            sqlTotal.append(sql +"\n");
            System.out.println(sql.toString());
        }
//        System.out.println(sqlTotal.toString());
//        outputStream.write(sqlTotal.toString().getBytes());
//        outputStream.close();

    }
}
