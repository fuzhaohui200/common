package org.shine.common.map.osm;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.StringReader;

/**
 * SAXReader 公用类
 * Created by chong.song on 14-2-28.
 */
public class SAXReaderUtil {
    private static SAXReader reader = new SAXReader();

    /**
     * 解析并且返回root节点
     * @param xml   xml
     * @return  返回的root节点
     * @throws org.dom4j.DocumentException
     */
    public static Element parseAndReturnRoot(String xml) throws DocumentException {
        Document doc = reader.read(new StringReader(xml));
        return doc.getRootElement();
    }
}
