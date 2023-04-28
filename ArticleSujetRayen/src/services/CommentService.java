/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entites.Article;
import entites.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author Houssem Charef
 */
public class CommentService implements IService<Comment> {

    private Connection con;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;

    public CommentService() {
        con = DataSource.getInstance().getCnx();
    }

    @Override
    public boolean insert(Comment t) {
        String sql = "INSERT INTO `comment`( `comment`, `id_article`, created_on) VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, t.getComment());
            ps.setInt(2, t.getArticle().getId());
            ps.setTimestamp(3, t.getCreated_on());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean update(Comment t) {
        String sql = "UPDATE `comment` SET`comment`=?,`id_article`=? WHERE  `id`=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setString(1, t.getComment());
            ps.setInt(2, t.getArticle().getId());
            ps.setInt(3, t.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Comment t) {
        String sql = "DELETE FROM `comment` WHERE id=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, t.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Comment> getAll() {
        String sql = "SELECT * FROM `comment` INNER JOIN article on article.id=comment.id_article;";

        List<Comment> list = new ArrayList<>();
        try {
            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id_article"));
                article.setDescription(rs.getString("descripton"));
                article.setImg(rs.getString("img_article"));
                article.setTitre(rs.getString("titre"));
                article.setAuteur("auteur");
                article.setLike(rs.getInt("likes"));
                article.setDislike(rs.getInt("dislikes"));

                Comment comment = new Comment();
                comment.setArticle(article);
                comment.setId(rs.getInt("id"));
                comment.setCreated_on(rs.getTimestamp("created_on"));
                comment.setComment(rs.getString("comment"));

                list.add(comment);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Comment getOne(int id) {
        String sql = "SELECT * FROM `comment` INNER JOIN article on article.id=comment.id_article where comment.id = " + id;
        Comment comment = new Comment();

        try {
            st = con.createStatement();

            rs = st.executeQuery(sql);

            rs.next();
            Article article = new Article();
            article.setId(rs.getInt("id_article"));
            article.setDescription(rs.getString("descripton"));
            article.setImg(rs.getString("img_article"));
            article.setTitre(rs.getString("titre"));
            article.setAuteur("auteur");
            article.setLike(rs.getInt("likes"));
            article.setDislike(rs.getInt("dislikes"));

            comment.setArticle(article);
            comment.setId(rs.getInt("id"));
            comment.setCreated_on(rs.getTimestamp("created_on"));
            comment.setComment(rs.getString("comment"));

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comment;
    }

    public List<Comment> getAllByArticle(int idArticle) {
        String sql = "SELECT * FROM `comment` INNER JOIN article on article.id=comment.id_article where article.id = " + idArticle;

        List<Comment> list = new ArrayList<>();
        try {
            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id_article"));
                article.setDescription(rs.getString("descripton"));
                article.setImg(rs.getString("img_article"));
                article.setTitre(rs.getString("titre"));
                article.setAuteur("auteur");
                article.setLike(rs.getInt("likes"));
                article.setDislike(rs.getInt("dislikes"));

                Comment comment = new Comment();
                comment.setArticle(article);
                comment.setId(rs.getInt("id"));
                comment.setCreated_on(rs.getTimestamp("created_on"));
                comment.setComment(rs.getString("comment"));

                list.add(comment);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
