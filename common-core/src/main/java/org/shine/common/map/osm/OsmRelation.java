package org.shine.common.map.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关系
 * Created by chong.song on 14-3-1.
 */
public class OsmRelation {
    private String id;
    private String version;
    private String changeset;
    private List<OsmRelationNumber> numberList = new ArrayList<OsmRelationNumber>();
    private Map<String,String> tagMap = new HashMap<String, String>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChangeset() {
        return changeset;
    }

    public void setChangeset(String changeset) {
        this.changeset = changeset;
    }

    public List<OsmRelationNumber> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<OsmRelationNumber> numberList) {
        this.numberList = numberList;
    }

    public Map<String, String> getTagMap() {
        return tagMap;
    }

    public void setTagMap(Map<String, String> tagMap) {
        this.tagMap = tagMap;
    }
}
