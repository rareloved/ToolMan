package com.andy.test.wsdl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.wsdl.*;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import com.andy.test.JsonUtils;
import com.ibm.wsdl.extensions.http.HTTPAddressImpl;
import com.ibm.wsdl.extensions.soap.SOAPAddressImpl;

/**
 * 使用WSDL4J工具解析wsdl文档
 *
 * @author zhuyongwei
 *         2015-03-12
 */
public class WSDLOperater {
    private String IPAddress = "";
    private String port = "";
    private Definition def;
    private Operation operation;
    private List<String> methods;

    /**
     * 初始化
     *
     * @param wsdl
     */
    public WSDLOperater(String wsdl) {
        WSDLFactory factory;
        try {
            factory = WSDLFactory.newInstance();
            WSDLReader reader = factory.newWSDLReader();
            reader.setFeature("javax.wsdl.verbose", true);
            reader.setFeature("javax.wsdl.importDocuments", true);
            def = reader.readWSDL(wsdl);//例如 "http://172.16.24.121:8080/WebService/hello?wsdl"
        } catch (WSDLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得wsdl的ip地址
     *
     * @return
     */
    public String getIPAddress() {
        Map services = def.getServices();
        Iterator it2 = services.values().iterator();
        while (it2.hasNext()) {
            Service service = (Service) it2.next();
            Iterator it3 = service.getPorts().values().iterator();
            while (it3.hasNext()) {
                Port port = (Port) it3.next();
                @SuppressWarnings("unchecked")
                List<Object> e = port.getExtensibilityElements();
                for (Object object : e) {
                    String URI = "";
                    if (object instanceof SOAPAddressImpl) {
                        URI = ((SOAPAddressImpl) object).getLocationURI();
                    } else if (object instanceof HTTPAddressImpl) {
                        URI = ((HTTPAddressImpl) object).getLocationURI();
                    }
                    if (!"".equals(URI)) {
                        String URI2 = URI.substring(URI.indexOf("//") + 2, URI.indexOf("/", 8));
                        if (URI2.indexOf(":") > -1) {
                            URI2 = URI2.substring(0, URI2.indexOf(":"));
                        }
                        try {
                            InetAddress inet = InetAddress.getByName(URI2);
                            IPAddress = inet.getHostAddress();
                        } catch (UnknownHostException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
            }
        }
        return IPAddress;
    }

    /**
     * 获得wsdl中的方法
     *
     * @return
     */
    public String getMethods() {
        StringBuffer methods = new StringBuffer("");

        @SuppressWarnings("unchecked")
        Map<String, PortType> portTypes = def.getPortTypes();
        Iterator<Map.Entry<String, PortType>> it1 = portTypes.entrySet().iterator();
        while (it1.hasNext()) {
            Entry<String, PortType> entry = (Entry<String, PortType>) it1.next();
            PortType portType = entry.getValue();
            @SuppressWarnings("unchecked")
            List<Operation> list = portType.getOperations();

            for (int i = 0; i < list.size(); i++) {
                Operation o = list.get(i);
                String method = o.getName();
                if (i == list.size() - 1)
                    methods.append(method);
                else
                    methods.append(method + ",");
            }
            break;
        }
        //System.out.println("-------------method:"+methods.toString());
        return methods.toString();
    }

    public String getPort() {
        return port;
    }

    public String getMethodParameters(){
        //解析消息，输入输出
        System.out.println("nMessages:");
        Map messages=def.getMessages();
        Iterator msgIter=messages.values().iterator();
        while(msgIter.hasNext())
        {
            Message msg=(Message)msgIter.next();
            if(!msg.isUndefined())
            {
                System.out.println(msg.getQName().getLocalPart());
                Iterator partIter=msg.getParts().values().iterator();
                while(partIter.hasNext())
                {
                    Part part=(Part) partIter.next();
                    // System.out.print("parameter name:"+part.getName()+"t");
                    System.out.print("parameter name:"+part.getName());
                    //  System.out.println("parameter type:"+part.getTypeName().getLocalPart());
                    System.out.print(" ......parameter element:"+part.getElementName().getLocalPart());
                    System.out.println(" ......parameter type:"+part.getTypeName());
                }
            }
        }
        System.out.println();
        return "";
    }

    public static void main(String[] args) {

//        WSDLOperater wsdl = new WSDLOperater("http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?wsdl");
        WSDLOperater wsdl = new WSDLOperater("http://localhost:8080/services/holidayService/holiday.wsdl");
//        WSDLOperater wsdl = new WSDLOperater("http://www.webservicex.net/globalweather.asmx?wsdl");
//        WSDLOperater wsdl = new WSDLOperater("http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl");
//        WSDLOperater wsdl = new WSDLOperater("http://172.16.24.121:8080/WebService/hello?wsdl");
        System.out.println("---------------------------------");
        System.out.println(wsdl.getIPAddress());
        System.out.println(wsdl.getMethods());
//        System.out.println(wsdl.getMethodParameters());
        System.out.println("---------------------------------");


        try {
            InetAddress inet = InetAddress.getByName("172.16.24.121");
            //System.out.println(inet.getHostAddress());

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
