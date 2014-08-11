package org.shine.common.search.utils1;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.*;

public class RadarSpecialSearchEngine {

    public static void main(String[] args) throws ParserException {
        try {
            TravelWordTable("D:\\workshop\\docs\\wordlist.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TravelWordTable(String filename) throws IOException {
        try {
            String buffer;
            FileWriter resultFile = null;
            PrintWriter myFile = null;

            String dstfile = filename + "_dsturl.txt";
            File writefile = new File(dstfile);
            if (!writefile.exists()) {
                writefile.createNewFile();
            }
            resultFile = new FileWriter(writefile);
            myFile = new PrintWriter(resultFile);
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((buffer = reader.readLine()) != null) {
                String url = "http://www.baidu.com/s?lm=0&si=&rn=10&ie=UTF-8&ct=0&wd=" + buffer + "&pn=0&ver=0&cl=3";
                getBaiduUrls(url, "UTF-8", myFile);
            }
            if (myFile != null)
                myFile.close();
            if (resultFile != null)
                resultFile.close();

        } catch (ParserException e) {
            e.printStackTrace();
        }


    }

    public static void getBaiduUrls(String url, String pageEncoding, PrintWriter writer) throws ParserException {
        NodeList nodeList = null;
        try {
            Parser parser = new Parser(url);
            parser.setEncoding(pageEncoding);                            // ���ý��������ʽ
            // Baidu ��������url���Ӻͱ���
            nodeList = parser.parse(new AndFilter(new HasAttributeFilter("target"),
                    new HasAttributeFilter("onclick")));
        } catch (ParserException e) {
            e.printStackTrace();
        }
        if (nodeList != null && nodeList.size() > 0) {                 // ѭ������ÿ��Url�ڵ�
            for (int i = 0; i < nodeList.size(); i++) {
                String urlLink = ((LinkTag) nodeList.elementAt(i)).extractLink();
                String LinkName = ((LinkTag) nodeList.elementAt(i)).getLinkText();

                if (urlLink.indexOf("bnu") == 0 || urlLink.indexOf("http") == 0)
                    System.out.println("��� " + i + " ���⣺" + LinkName);
                System.out.println("       ���ӣ�" + urlLink);
                writer.println(urlLink);
            }
        }
    }
}	 	  

