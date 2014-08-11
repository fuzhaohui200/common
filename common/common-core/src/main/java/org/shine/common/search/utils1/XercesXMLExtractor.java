package org.shine.common.search.utils1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XercesXMLExtractor {
    public static void main(String[] args) {
        getXmlNodeText("D:\\workshop\\docs\\tree.xml");
    }

    public static void getXmlNodeText(String filename) {
        // ��ȡDOM�������������
        DocumentBuilderFactory builderfactory = DocumentBuilderFactory.newInstance();
        try {
            // ��ȡ DocumentBuilderʵ��
            DocumentBuilder builder = builderfactory.newDocumentBuilder();
            File file = new File(filename);
            ////�� XML �ĵ���ȡ DOM �ĵ�ʵ��
            Document document = builder.parse(file);
            //��ȡĳ�ڵ�ļ���
            NodeList nodelist = document.getElementsByTagName("item");
            // ��ȡ�ڵ��б���ܳ���
            int listnum = nodelist.getLength();
            System.out.println("--------�ڵ�������" + listnum + "--------");
            for (int i = 0; i < listnum; i++) {
                // ��ȡ�ڵ�
                Element eltItem = (Element) nodelist.item(i);
                // ��ȡ�ڵ�ĸ�������
                Node eltTitle = eltItem.getElementsByTagName("title").item(0);
                Node eltLink = eltItem.getElementsByTagName("addr").item(0);
                Node eltDescription = eltItem.getElementsByTagName("description").item(0);
                String title = "";
                if (eltTitle != null) {
                    title = eltTitle.getFirstChild().getNodeValue();
                }
                String link = "";
                if (eltLink != null) {
                    link = eltLink.getFirstChild().getNodeValue();
                }
                String description = "";
                if (eltDescription != null) {
                    description = eltDescription.getFirstChild().getNodeValue();
                }
                // ������
                System.out.print("���⣺");
                System.out.println(title);
                System.out.print("���ӣ�");
                System.out.println(link);
                System.out.print("������");
                System.out.println(description);
                System.out.println("----------------------------\n");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

