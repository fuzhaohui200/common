package bocomm.models.TivoliModels;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TivoliRowModel {

    private int id;

    private String appName;

    private String auditPointName;

    private List<RuleGroup> listOfRuleGroup = new ArrayList<RuleGroup>();

    private List<TivoliFieldTableModel> listOfTivoliFieldTable = new ArrayList<TivoliFieldTableModel>();

    private boolean isWarn = true;

    private String errorMsgDescribe;

    public TivoliRowModel() {
        // TODO Auto-generated constructor stub
    }

    @XmlElement
    public List<RuleGroup> getListOfRuleGroup() {
        return listOfRuleGroup;
    }

    public void setListOfRuleGroup(List<RuleGroup> listOfRuleGroup) {
        this.listOfRuleGroup = listOfRuleGroup;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public List<TivoliFieldTableModel> getListOfTivoliFieldTable() {
        return listOfTivoliFieldTable;
    }

    public void setListOfTivoliFieldTable(
            List<TivoliFieldTableModel> listOfTivoliFieldTable) {
        this.listOfTivoliFieldTable = listOfTivoliFieldTable;
    }

    public void addListOfTivoliFieldTable(TivoliFieldTableModel tivoliFieldTable) {
        this.listOfTivoliFieldTable.add(tivoliFieldTable);
    }

    @XmlElement
    public String getName() {
        return appName + id;
    }

    public void setName(String appName) {
        this.appName = appName;
    }

    @XmlElement
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @XmlElement
    public String getAuditPointName() {
        return auditPointName;
    }

    public void setAuditPointName(String auditPointName) {
        this.auditPointName = auditPointName;
    }

    @XmlElement
    public boolean isWarn() {
        return isWarn;
    }

    public void setWarn(boolean isWarn) {
        this.isWarn = isWarn;
    }

    @XmlElement
    public String getErrorMsgDescribe() {
        return errorMsgDescribe;
    }

    public void setErrorMsgDescribe(String errorMsgDescribe) {
        this.errorMsgDescribe = errorMsgDescribe;
    }
}
