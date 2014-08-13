package org.shine.common.excelutil.xlsx.examples;

import org.shine.common.excelutil.xlsx.XxlsAbstract;

import java.sql.SQLException;
import java.util.List;

public class XxlsPrint extends XxlsAbstract {

    public static void main(String[] args) throws Exception {
        XxlsPrint howto = new XxlsPrint();
        howto.processOneSheet("D:/users.xlsx", 1);
        // howto.processAllSheets("F:/new.xlsx");
    }

    @Override
    public void optRows(int sheetIndex, int curRow, List<String> rowlist)
            throws SQLException {
        for (int i = 0; i < rowlist.size(); i++) {
            System.out.print("'" + rowlist.get(i) + "',");
        }
        System.out.println();
    }
}
