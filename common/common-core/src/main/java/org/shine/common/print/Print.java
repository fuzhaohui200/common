package org.shine.common.print;

import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Print {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        PrinterJob myPrtJob = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        PrintRequestAttribute attribute = new PrintRequestAttribute() {

            /**
             *
             */
            private static final long serialVersionUID = -4896804694090850430L;

            @Override
            public String getName() {
                // TODO Auto-generated method stub
                return "fuzhaohui";
            }

            @Override
            public Class<? extends Attribute> getCategory() {
                // TODO Auto-generated method stub
                return null;
            }
        };

        printRequestAttributeSet.add(attribute);

        // PageFormat pageFormat = myPrtJob.defaultPage();
        // myPrtJob.setPrintable(this, pageFormat);
        if (myPrtJob.printDialog()) {
            try {
                myPrtJob.print(printRequestAttributeSet);
            } catch (PrinterException pe) {
                pe.printStackTrace();
            }
        }
    }

}
