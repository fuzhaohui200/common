package org.shine.hibernate.test.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bbs_bulletinsort")
public class Bulletinsort {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "bulletinsort_id")
    private Integer bulletinsortId;
    @Column(name = "sortName")
    private String sortName;
    @Column(name = "createTime")
    private Date createTime;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Bulletin> bulletins = new HashSet<Bulletin>(0);

    public Integer getBulletinsortId() {
        return bulletinsortId;
    }

    public void setBulletinsortId(Integer bulletinsortId) {
        this.bulletinsortId = bulletinsortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Bulletin> getBulletins() {
        return bulletins;
    }

    public void setBulletins(Set<Bulletin> bulletins) {
        this.bulletins = bulletins;
    }


}