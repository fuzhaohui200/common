package org.shine.common.search.utils2;

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
            TravelWordTable("e:\\test.txt");
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
            parser.setEncoding(pageEncoding);                            // 设置解析编码格式
            // Baidu 检索结果的url连接和标题
            nodeList = parser.parse(new AndFilter(new HasAttributeFilter("target"),
                    new HasAttributeFilter("onclick")));
        } catch (ParserException e) {
            e.printStackTrace();
        }
        if (nodeList != null && nodeList.size() > 0) {                 // 循环遍历每个Url节点
            for (int i = 0; i < nodeList.size(); i++) {
                String urlLink = ((LinkTag) nodeList.elementAt(i)).extractLink();
                String LinkName = ((LinkTag) nodeList.elementAt(i)).getLinkText();

                if (urlLink.indexOf("bnu") == 0 || urlLink.indexOf("http") == 0)
                    System.out.println("结果 " + i + " 标题：" + LinkName);
                System.out.println("       链接：" + urlLink);
                writer.println(urlLink);
            }
        }
    }
}	 	  

