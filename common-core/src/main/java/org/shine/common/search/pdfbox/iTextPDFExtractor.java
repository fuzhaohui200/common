package org.shine.common.search.pdfbox;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class iTextPDFExtractor {

    public static void iTextGetPDF(String filename) {
        Document doc = new Document();                     // 创建空白文档对象
        try {

            PdfReader reader = new PdfReader(filename); // 生成PdfReader对象
            int pagenum = reader.getNumberOfPages();     // 读取文档页数
            System.out.println("文档页数 ：" + pagenum);

            for (int i = 0; i < pagenum; i++)             // 读取文档内容
            {
                String txtContent = reader.getPageContent(1).toString();
                System.out.println("文档内容 ：" + txtContent);
            }
            doc.close();                                 //  关闭文档对象，释放资源
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        iTextGetPDF("H:\\2012_IM产品开发\\openmeetings\\Openmeetings.pdf");
    }

}
