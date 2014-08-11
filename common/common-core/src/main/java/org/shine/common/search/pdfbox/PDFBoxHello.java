package org.shine.common.search.pdfbox;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.FileInputStream;

public class PDFBoxHello {
    public static void main(String args[]) {
        PDFBoxHello pdfbox = new PDFBoxHello();                        // 生成PDFBoxHello对象
        try {
            // 获取文档纯文本内容
            String doctext = pdfbox.GetTextFromPdf("H:\\2012_IM产品开发\\openmeetings\\Openmeetings.pdf");
            System.out.println("文件内容 : ");
            System.out.println(doctext);
            System.out.println("文件结束 . ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取PDF内纯文本信息
    public String GetTextFromPdf(String filename) throws Exception {
        FileInputStream instream = new FileInputStream(filename);    // 根据指定文件创建输入流
        PDFParser parser = new PDFParser(instream);                // 创建PDF解析器
        parser.parse();                                              // 执行PDF解析过程

        PDDocument pdfdocument = parser.getPDDocument();             // 获取解析器的PDF文档对象
        PDFTextStripper pdfstripper = new PDFTextStripper();         // 生成PDF文档内容剥离器
        String contenttxt = pdfstripper.getText(pdfdocument);        // 利用剥离器获取文档

        System.out.println("文件长度 : " + contenttxt.length() + "\n");
        return contenttxt;

    }
}
