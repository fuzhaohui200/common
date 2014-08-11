package bocomm.models.TivoliModels;

import javax.xml.bind.annotation.XmlElement;

public class TivoliFieldTableModel {

    private String id;

    private String name;

    private String value;

    public TivoliFieldTableModel() {
        // TODO Auto-generated constructor stub
    }

    @XmlElement(name = "key")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
