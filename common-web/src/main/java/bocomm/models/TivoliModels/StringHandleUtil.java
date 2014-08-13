package bocomm.models.TivoliModels;

import java.util.ArrayList;
import java.util.List;

public class StringHandleUtil {

    public static void main(String[] args) {
        String str = "Filesystem,GB blocks,Free,%Used,%Iused,Mount on\n/dev/LVECM,50.00,38.29 24,2395,1%,10/25/12 15:46:30\n/dev/LVECMSHARE,50.00,48.74 3,508456,5%,10/25/12 15:46:30 ";
        System.out.println(formatStringToTivoliTable("test", "test", str));
        System.out.println(str);
    }

    public static List<TivoliRowModel> formatStringToTivoliTable(String appName, String auditPointName, String tivoliStr) {
        List<TivoliRowModel> tivoliRowModelList = new ArrayList<TivoliRowModel>();
        List<String> rowTitils = new ArrayList<String>();
        String[] tivoliRowModelStrs = tivoliStr.split("\n");
        for (int rowSize = 0; rowSize < tivoliRowModelStrs.length; rowSize++) {
            String[] columnVals = tivoliRowModelStrs[rowSize].split(",");

            List<TivoliFieldTableModel> tivoliFieldTableModelList = new ArrayList<TivoliFieldTableModel>();
            for (int i = 0; i < columnVals.length; i++) {
                if (rowSize == 0) {
                    rowTitils.add(columnVals[i]);
                } else {
                    TivoliFieldTableModel tivoliFieldTableModel = new TivoliFieldTableModel();
                    tivoliFieldTableModel.setId("" + rowSize);
                    tivoliFieldTableModel.setName(rowTitils.get(i));
                    tivoliFieldTableModel.setValue(columnVals[i]);
                    tivoliFieldTableModelList.add(tivoliFieldTableModel);
                }
            }
            if (rowSize != 0) {
                TivoliRowModel tivoliRowModel = new TivoliRowModel();
                tivoliRowModel.setAppName(appName);
                tivoliRowModel.setId(rowSize);
                tivoliRowModel.setAuditPointName(auditPointName);
                tivoliRowModel.setListOfTivoliFieldTable(tivoliFieldTableModelList);
                tivoliRowModelList.add(tivoliRowModel);
            }
        }
        return tivoliRowModelList;
    }

}
