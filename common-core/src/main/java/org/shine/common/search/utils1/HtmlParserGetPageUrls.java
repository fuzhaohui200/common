package org.shine.common.search.utils1;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class HtmlParserGetPageUrls {

    public static void main(String[] args) throws ParserException {

        try {

            getHtmlUrls("http://www.bnu.edu.cn/", "UTF-8");
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    public static void getHtmlUrls(String url, String pageEncoding) throws ParserException {
        NodeList nodeList = null;

        try {
            Parser parser = new Parser(url);
            parser.setEncoding(pageEncoding);                            // ���ý��������ʽ
            // ����ʹ������filter��ȡ��url����
            //nodeList = parser.parse(new TagNameFilter("A"));          // ʹ��TagNameFilter
            nodeList = parser.parse(new NodeClassFilter(LinkTag.class)); // ʹ��NodeClassFilter
        } catch (ParserException e) {
            e.printStackTrace();
        }

        if (nodeList != null && nodeList.size() > 0) {                 // ѭ������ÿ��Url�ڵ�
            for (int i = 0; i < nodeList.size(); i++) {
                String urlLink = ((LinkTag) nodeList.elementAt(i)).extractLink();
                String LinkName = ((LinkTag) nodeList.elementAt(i)).getLinkText();

                if (urlLink.indexOf("bnu") == 0 || urlLink.indexOf("http") == 0)
                    //System.out.println(LinkName +" : "+ urlLink);
                    System.out.println(urlLink);
            }
        }

    }


}	 	  

