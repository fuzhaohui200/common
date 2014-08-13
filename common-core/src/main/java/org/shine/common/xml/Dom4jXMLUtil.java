package org.shine.common.xml;

import org.dom4j.*;
import org.dom4j.io.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class Dom4jXMLUtil {

    // Creating a new XML document
    private static Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("root");

        Element author1 = root.addElement("author")
                .addAttribute("name", "James")
                .addAttribute("location", "UK")
                .addText("James Strachan");

        Element author2 = root.addElement("author")
                .addAttribute("name", "Bob")
                .addAttribute("location", "US")
                .addText("Bob McWhirter");
        return document;
    }

    public static void main(String[] args) {

        String text = "<person> <name>James</name> </person>";
        try {
            //Document document = DocumentHelper.parseText(text);
            Document document = createDocument();
            new Dom4jXMLUtil().write(document);
        } catch (IOException e) {
            e.printStackTrace();
        }

//		Document document;
//		try {
//			document = new XMLUtil().parse(new File("E://output.xml").toURL());
//			String text = document.asXML();
//			System.out.println(text);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


    }

    public Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    @SuppressWarnings("rawtypes")
    public void bar(Document document) throws DocumentException {

        Element root = document.getRootElement();

        // iterate through child elements of root
        for (Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            // do something
        }

        // iterate through child elements of root with element name "foo"
        for (Iterator i = root.elementIterator("foo"); i.hasNext(); ) {
            Element foo = (Element) i.next();
            // do something
        }

        // iterate through attributes of root
        for (Iterator i = root.attributeIterator(); i.hasNext(); ) {
            Attribute attribute = (Attribute) i.next();
            // do something
        }
    }

    // Powerful Navigation with XPath
    public void bar1(Document document) {
        List list = document.selectNodes("//foo/bar");

        Node node = document.selectSingleNode("//foo/bar/author");

        String name = node.valueOf("@name");
    }

    public void findLinks(Document document) throws DocumentException {

        List list = document.selectNodes("//a/@href");

        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Attribute attribute = (Attribute) iter.next();
            String url = attribute.getValue();
        }
    }

    // Fast Looping
    public void treeWalk(Document document) {
        treeWalk(document.getRootElement());
    }

    public void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                treeWalk((Element) node);
            } else {
                // do something....
            }
        }
    }

    public void write(Document document) throws IOException {

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter("E://output1.xml"));
        writer.write(document);
        writer.close();

        // Pretty print the document to System.out
        OutputFormat format = OutputFormat.createPrettyPrint();
        writer = new XMLWriter(System.out, format);
        writer.write(document);

        // Compact format to System.out
        format = OutputFormat.createCompactFormat();
        writer = new XMLWriter(System.out, format);
        writer.write(document);
    }

    public Document styleDocument(Document document, String stylesheet)
            throws Exception {

        // load the transformer using JAXP
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(
                stylesheet));

        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);

        // return the transformed document
        Document transformedDoc = result.getDocument();
        return transformedDoc;
    }

}
