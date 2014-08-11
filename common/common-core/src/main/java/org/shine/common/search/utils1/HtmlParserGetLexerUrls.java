package org.shine.common.search.utils1;

import org.htmlparser.Node;
import org.htmlparser.http.ConnectionManager;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.ParserException;


public class HtmlParserGetLexerUrls {

    public static void main(String[] args) throws ParserException {
        try {
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

}	 	  

