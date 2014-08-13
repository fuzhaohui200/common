package org.shine.hibernate.test.entity;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
public class Comment {

    private Long id;
    private String comment;
    private BlogPost blogPost;


    @ManyToOne(fetch = FetchType.EAGER)
    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "COMMENT_ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}