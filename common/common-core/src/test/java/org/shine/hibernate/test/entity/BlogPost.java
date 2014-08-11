package org.shine.hibernate.test.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "BlogPost")
public class BlogPost implements Serializable {

    private static final long serialVersionUID = 5402285050275769205L;

    private Long id;
    private String title;
    private String content;
    private Boolean published;
    private Date date;
    private String path;
    private Set<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "BLOGPOST_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column
    @Type(type = "yes_no")
    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    @Column
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "blogPost")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}