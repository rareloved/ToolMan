package com.andy.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangshouzheng on 2016/9/30.
 */
public class TransferData {
    private static String[][]data = new String[9][4];
    static {
        data[0] = new String[]{"1","第一级","",""};
        data[1] = new String[]{"2","第二级1","",""};
        data[2] = new String[]{"3","","no1","第二级1"};
        data[3] = new String[]{"3","","no1","第二级1"};
        data[4] = new String[]{"3","","no1","第二级1"};
        data[5] = new String[]{"2","第二级2","",""};
        data[6] = new String[]{"3","","no2","第二级2"};
        data[7] = new String[]{"3","","no2","第二级2"};
        data[8] = new String[]{"3","","no2","第二级2"};
    }
    public static void main(String[] args) {
        List<DataPo> dataPoList = new ArrayList<DataPo>();
        System.out.println("层次\t名称\t关联_编号\t关联_名称");
        for (String[] outer:data){
            System.out.println(outer[0]+"\t\t"+outer[1]+"\t\t"+outer[2]+"\t\t"+outer[3]);
            //赋值到DataPo
            DataPo dataPo = new DataPo();
            dataPo.setLevel(outer[0]);
            dataPo.setName(outer[1]);
            dataPo.setRelate_id(outer[2]);
            dataPo.setRelate_name(outer[3]);
            dataPoList.add(dataPo);
        }
        System.out.println("这里是分界线----------------------");
        for (DataPo po: dataPoList) {
            System.out.println(po.getLevel()+"\t\t"+po.getName()+"\t\t"+po.getRelate_id()+"\t\t"+po.getRelate_name());
        }
    }
    static class DataPo{
        //层次
        private String level;
        //名称
        private String name;
        //关联_编号
        private String relate_id;
        //关联_名称
        private String relate_name;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelate_id() {
            return relate_id;
        }

        public void setRelate_id(String relate_id) {
            this.relate_id = relate_id;
        }

        public String getRelate_name() {
            return relate_name;
        }

        public void setRelate_name(String relate_name) {
            this.relate_name = relate_name;
        }
    }
}
