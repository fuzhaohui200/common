package org.shine.common.search.utils1;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLExtractor {

    public static void getXMLContent(String filename) {
        SAXBuilder sb = new SAXBuilder();
        try {
            Document doc = sb.build(filename); // 生成文档对象
            Element root = doc.getRootElement(); // 获取根节点
            String str1 = root.getAttributeValue("comment"); // 获取根节点的comment属性
            System.out.println("根节点说明 : " + str1);

            String str2 = root.getChild("Listener").getAttributeValue(
                    "className"); // 获取Listner节点的className属性
            System.out.println("Listener 节点 className 属性 : " + str2);
            String str3 = root.getChild("Service").getAttributeValue("name"); // 获取Service节点的name属性
            System.out.println("Service  节点  Name     属性 : " + str3);

            XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat()); // 生成XML的输出对象
            String outStr = xmlOut.outputString(root); // 输出根节点内容字符串
            System.out.println("");
            System.out.println(outStr); // 输出根节点内容
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getXMLContent("E:\\server.xml");
    }
}
