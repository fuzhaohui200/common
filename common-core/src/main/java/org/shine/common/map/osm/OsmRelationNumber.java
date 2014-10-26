package org.shine.common.map.osm;

/**
 * 关系的number
 * Created by chong.song on 14-3-1.
 */
public class OsmRelationNumber {
    private String type;
    private String role;
    private String ref;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
