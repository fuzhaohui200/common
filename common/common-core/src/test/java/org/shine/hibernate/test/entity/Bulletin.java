package org.shine.hibernate.test.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bbs_bulletin")
public class Bulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "bulletin_id")
    private Integer bulletinId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bulletinsort_id")
    private Bulletinsort bulletinsort = new Bulletinsort();
    @Column(name = "theme")
    private String theme;
    @Column(name = "content")
    private String content;
    @Column(name = "authorid")
    private Integer authorid;
    @Column(name = "createTime")
    private Date createTime;
    @Column(name = "commentId")
    private Integer commentId;
    @Column(name = "status")
    private Integer status;


    public Integer getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(Integer bulletinId) {
        this.bulletinId = bulletinId;
    }


    public Bulletinsort getBulletinsort() {
        return bulletinsort;
    }

    public void setBulletinsort(Bulletinsort bulletinsort) {
        this.bulletinsort = bulletinsort;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}