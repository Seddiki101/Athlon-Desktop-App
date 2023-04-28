/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.sql.Timestamp;

/**
 *
 * @author Houssem Charef
 */
public class Comment {

    private int id;
    private String comment;
    private Article article;
    private Timestamp created_on = new Timestamp(System.currentTimeMillis());

    public Comment() {
    }

    public Comment(int id, String comment, Article article, Timestamp created_on) {
        this.id = id;
        this.comment = comment;
        this.article = article;
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Timestamp getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Timestamp created_on) {
        this.created_on = created_on;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", comment=" + comment + ", article=" + article + ", created_on=" + created_on + '}';
    }

}
