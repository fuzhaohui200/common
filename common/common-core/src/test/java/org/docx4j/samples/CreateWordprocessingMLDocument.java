package org.docx4j.samples;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.STPageOrientation;

import java.math.BigInteger;

/**
 * ����ooxml�ĵ�
 *
 * @version 1.0
 * @��ܰ��
 */
public class CreateWordprocessingMLDocument {

    public static void main(String[] args) throws Exception {

        System.out.println("Creating package..");
        //�����ĵ��������
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();

        //�������ַ���-1(��ݷ���,������ϸ����)
        wordMLPackage.getMainDocumentPart().addStyledParagraphOfText("Title", "����ͨ���� (www.xerllent.cn)�����ĵ�����");
        wordMLPackage.getMainDocumentPart().addParagraphOfText("����ͨ��Ŀ(Xerllent Projects)��һ�����j2ee��������ҵ��Ϣ��ϵͳ�з��ƻ�!");


        //�������ַ���-2(�����취,���Բ����κ�����)
        /**
         *  To get bold text, you must set the run's rPr@w:b,
         *  so you can't use the createParagraphOfText convenience method
         *  org.docx4j.wml.P p = wordMLPackage.getMainDocumentPart().createParagraphOfText("text"); //�����޸�ʽ�ı������
         * */

        org.docx4j.wml.ObjectFactory factory = new org.docx4j.wml.ObjectFactory();//�ĵ��Ӷ��󹤳�

        org.docx4j.wml.P p = factory.createP();//��������P

        //�����ı���R����
        org.docx4j.wml.R run = factory.createR();//�����ı���R
        org.docx4j.wml.Text t = factory.createText();//�����ı�������Text
        t.setValue("text");
        run.getRunContent().add(t);//Text��ӵ�R
        //�����ı���R����,Optionally, set pPr/rPr@w:b
        org.docx4j.wml.RPr rpr = factory.createRPr();
        org.docx4j.wml.BooleanDefaultTrue b = new org.docx4j.wml.BooleanDefaultTrue();//������ȱʡֵ��boolen���Զ���
        b.setVal(true);
        rpr.setB(b);
        run.setRPr(rpr);//�����ı���R����

        p.getParagraphContent().add(run);//R��ӵ�P

        // ����Ĭ�ϵĶ�������,�����뵽���������ȥ
        org.docx4j.wml.PPr ppr = factory.createPPr();
        org.docx4j.wml.ParaRPr paraRpr = factory.createParaRPr();
        ppr.setRPr(paraRpr);
        p.setPPr(ppr);//��������PPr��ӵ�P

        //��P������ӵ��ĵ���
        wordMLPackage.getMainDocumentPart().addObject(p);


        //��̬�����ӡҳ�漰��������,��ʱһ��A3����,ҳ���2��������,�Ծ�ҳ��
        org.docx4j.wml.SectPr sp = factory.createSectPr();
        org.docx4j.wml.SectPr.PgSz pgsz = factory.createSectPrPgSz();//<w:pgSz w:w="23814" w:h="16840" w:orient="landscape" w:code="8"/>
        pgsz.setW(BigInteger.valueOf(23814L));
        pgsz.setH(BigInteger.valueOf(16840L));
        pgsz.setOrient(STPageOrientation.LANDSCAPE);
        pgsz.setCode(BigInteger.valueOf(8L));
        sp.setPgSz(pgsz);

        org.docx4j.wml.SectPr.PgMar pgmar = factory.createSectPrPgMar();//<w:pgMar w:top="1440" w:right="1440" w:bottom="1440" w:left="1440" w:header="720" w:footer="720" w:gutter="0"/>
        pgmar.setTop(BigInteger.valueOf(1440));
        pgmar.setRight(BigInteger.valueOf(1440));
        pgmar.setBottom(BigInteger.valueOf(1440));
        pgmar.setLeft(BigInteger.valueOf(1440));
        pgmar.setHeader(BigInteger.valueOf(720));
        pgmar.setFooter(BigInteger.valueOf(720));
        sp.setPgMar(pgmar);

        org.docx4j.wml.CTColumns cols = factory.createCTColumns();//<w:cols w:num="2" w:space="425"/>
        cols.setNum(BigInteger.valueOf(2));
        cols.setSpace(BigInteger.valueOf(425));
        sp.setCols(cols);

        org.docx4j.wml.CTDocGrid grd = factory.createCTDocGrid();//<w:docGrid w:linePitch="360"/>
        grd.setLinePitch(BigInteger.valueOf(360));
        sp.setDocGrid(grd);

        wordMLPackage.getMainDocumentPart().addObject(sp);


        // �������ַ���-3(��Ӽ���ݵĲ������ݷ���,���Բ����κ�����,��������Ϥooxml�ĵ���ʽ)
        //�Զ����ǩת����ʱ��,�����xmlns:w=\"http: //schemas.openxmlformats.org/wordprocessingml/2006/main\"���
        String str = "<w:p xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" >" +
                "<w:r>" +
                "<w:rPr><w:b /></w:rPr>" +
                "<w:t>Bold, just at w:r level</w:t>" +
                "</w:r>" +
                "</w:p>";
        wordMLPackage.getMainDocumentPart().addObject(org.docx4j.XmlUtils.unmarshalString(str));

        //�Զ����ǩת����ʱ��,�����xmlns:w=\"http: //schemas.openxmlformats.org/wordprocessingml/2006/main\"���
        String str1 = "<w:sectPr xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" w:rsidR=\"00F10179\" w:rsidRPr=\"00CB557A\" w:rsidSect=\"001337D5\"><w:pgSz w:w=\"23814\" w:h=\"16840\" w:orient=\"landscape\" w:code=\"8\"/><w:pgMar w:top=\"1440\" w:right=\"1440\" w:bottom=\"1440\" w:left=\"1440\" w:header=\"720\" w:footer=\"720\" w:gutter=\"0\"/><w:cols w:num=\"2\" w:space=\"425\"/><w:docGrid w:linePitch=\"360\"/></w:sectPr>";
        wordMLPackage.getMainDocumentPart().addObject(org.docx4j.XmlUtils.unmarshalString(str1));

        System.out.println(".. done!");
        // Now save it
        wordMLPackage.save(new java.io.File(System.getProperty("user.dir") + "/sample-docs/bolds.docx"));
        System.out.println("Done.");
    }
}