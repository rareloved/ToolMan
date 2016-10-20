package com.andy.test;

import net.sourceforge.jpcap.capture.*;
import net.sourceforge.jpcap.net.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CapturePacketTest implements RawPacketListener,PacketListener{

    public static int number = 1;
    Map<String, String> statics = new HashMap<String, String>();


    public static void main(String[] args)throws Exception{


        PacketCapture pcap = new PacketCapture();
        //查看所有的网卡
        String[] capDevices = PacketCapture.lookupDevices();
        int capdevice_num = capDevices.length;
        //打印网卡列表
        System.out.println("---------网卡列表:-------------------------");
        for(int i = 0 ;i<capdevice_num;i++){
            System.out.println("第"+(i+1)+"号网卡:\n"+capDevices[i]);
        }
        System.out.println("---------默认采用6号网卡,下面开始捕获:----------");
        //暂停2秒
        for(int i =0 ;i<20;i++){
            Thread.sleep (100);
            System.out.print(">>");
        }
        System.out.println();
        //获取第6号网卡的物理地址,我的是6号网卡,你的并不一定是,在网卡列表中查看你说用的网卡,并选择序号,capDevices[0]表示1号网卡
        String capdevice_6 = capDevices[5].split("\n")[0];
        // 开始捕获,并设置为混杂模式
        pcap.open(capdevice_6,true);
        //递归调用
        CapturePacketTest t1= new CapturePacketTest();
        //原始数据包监听
        pcap.addRawPacketListener(t1);
        //数据包监听
        pcap.addPacketListener(t1);
        //结束捕获
        pcap.capture(-1);
    }
    //接口实现,没用
    public void rawPacketArrived(RawPacket rawPacket) {
        // TODO 自动生成的方法存根

    }

    //当获取到IP数据包
    public void packetArrived(Packet packet) {
        //判断是否是IP数据包,如果是,则进行统计
        if( packet instanceof IPPacket){
            IPPacket ippacket = ((IPPacket)packet);
            //获取当前时间
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String current_time=sdf.format(d);
            //String echo = "IP包个数:"+(number++)+"||当前时间("+current_time+")::源地址("+ippacket.getSourceAddress()+")-->目的地址("+ippacket.getSourceAddress()+")";
            String echo = "Number of IP Package:"+(number++)+"||Current Time("+current_time+")::Source IP:("+ippacket.getSourceAddress()+")-->Destination IP("+ippacket.getDestinationAddress()+")";
            //String echo ="版本:"+ippacket.getVersion()+"头长度"+ippacket.getHeaderLength()+"服务类型:"+ippacket.getTypeOfService()+"数据包总长度:"+ippacket.getLength()+"数据包标示"+ippacket.getId()+"分段标志"+ippacket.getFragmentFlags()+"分段偏移值"+ippacket.getFragmentOffset()+"生存时间"+ippacket.getTimeToLive()+"上层3协议类型"+ippacket.getEthernetProtocol()+"头校验和"+ippacket.getChecksum()+"源地址"+ippacket.getSourceAddress()+"目的地址"+ippacket.getDestinationAddress() ;
            System.out.println(echo);
            //statics.put(ippacket.getSourceAddress(),"1");
            //对文件进行写入
            try {
                // 打开一个随机访问文件流，按读写方式
                RandomAccessFile randomFile = new RandomAccessFile("H://capture.txt", "rw");
                // 文件长度，字节数
                long fileLength = randomFile.length();
                //将写文件指针移到文件尾。
                randomFile.seek(fileLength);
                randomFile.writeBytes(ippacket.getSourceAddress()+"\r\n");
                randomFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}