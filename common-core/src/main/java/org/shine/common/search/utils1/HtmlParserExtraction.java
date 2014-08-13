package org.shine.common.search.utils1;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;


public class HtmlParserExtraction {

    public static void main(String[] args) throws ParserException {

        try {

            //ParseHtmlText("http://www.bnu.edu.cn/","UTF-8");
            //getHtmlUrls("http://www.bnu.edu.cn/","UTF-8");
            //getBaiduUrls("http://www.baidu.com/s?lm=0&si=&rn=10&ie=UTF-8&ct=0&wd=Lucene&pn=0&ver=0&cl=3","UTF-8");

            getLexerUrls("http://www.bnu.edu.cn/", "UTF-8");
        } catch (ParserException e) {
            e.printStackTrace();
        }

    }


    public static void getLexerUrls(String url, String pageEncoding) throws ParserException {
        Node node = null;
        Lexer lexer = null;

        try {

            ConnectionManager connmgr;

            connmgr = Page.getConnectionManager();           // ������ӹ�����
            lexer = new Lexer(connmgr.openConnection(url));  // ��ɷ�����

            lexer.getPage().setEncoding(pageEncoding);       // ������ҳ����

            node = lexer.nextNode();
            while (node != null) {                           // ѭ������ÿ���ڵ�
                System.out.println(node.toString());
                node = lexer.nextNode();

            }
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
                if (urlLink.indexOf("bnu") == 0 || urlLink.indexOf("http") == 0)
                    //System.out.println(LinkName +" : "+ urlLink);
                    System.out.println(urlLink);
            }
        }

    }

    public static void getBaiduUrls(String url, String pageEncoding) throws ParserException {
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
            }
        }
    }

    public static void ParseHtmlText(String url, String pageEncoding) throws ParserException {
        Parser parser = new Parser(url);                              // ����Ŀ����վ
        parser.setEncoding(pageEncoding);                             // ���ý��������ʽ

        TextExtractingVisitor visitor = new TextExtractingVisitor(); // ����ı����ݳ�ȡ����

        NodeFilter textFilter = new NodeClassFilter(TextNode.class);  // ����ı�������

        NodeList nodes = parser.extractAllNodesThatMatch(textFilter); // �����ı������������ĵ�

        for (int i = 0; i < nodes.size(); i++) {
            TextNode textnode = (TextNode) nodes.elementAt(i);        // ��ȡ�ı��ڵ�
            String line = textnode.toPlainTextString().trim();        // ת���ɴ��ı�
            if (line.equals("")) continue;
            System.out.println(line);
        }

        parser.visitAllNodesWith(visitor);                           // ������ҳ���нڵ�
        System.out.println(visitor.getExtractedText());               // �����ҳ����

    }

}	 	  

