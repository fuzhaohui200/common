package com.ces.portal.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class ConfigXMLUtil {

    /**
     * 解析XML文件
     *
     * @param file
     * @return
     * @throws DocumentException
     */
    public static Document parseXmlFile(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }

    /**
     * 输出写到XML文件里
     *
     * @param document
     * @param fileWriter
     * @throws IOException
     */
    public static void write(Document document, Writer fileWriter) throws IOException {
        XMLWriter writer = new XMLWriter(fileWriter);
        writer.write(document);
        writer.close();
    }

    /**
     * 输出写到XML文件里
     *
     * @param document
     * @param fileWriter
     * @throws IOException
     */
    public static void writeStream(Document document, OutputStream out) throws IOException {
        XMLWriter writer = new XMLWriter(out);
        writer.write(document);
        writer.close();
    }

}
