package org.shine.common.map.osm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chong.song on 14-2-28.
 */
public class OsmNode {
    private String nodeId;

    //经度
    private Double lon;
    //纬度
    private Double lat;
    private int changeSet;
    private int version;
    private Map<String,String> tagMap = new HashMap<String, String>();
    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public int getChangeSet() {
        return changeSet;
    }
    public void setChangeSet(int changeSet) {
        this.changeSet = changeSet;
    }
    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public Map<String, String> getTagMap() {
        return tagMap;
    }

    public void setTagMap(Map<String, String> tagMap) {
        this.tagMap = tagMap;
    }
}
