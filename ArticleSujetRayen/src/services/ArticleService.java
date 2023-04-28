/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entites.Article;
import entites.Sujet;
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
 * @author wasli rayen
 */
public class ArticleService implements IService<Article> {

    private Connection con;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;

    public ArticleService() {
        con = DataSource.getInstance().getCnx();
    }

    @Override
    public boolean insert(Article t) {
        String sql = "INSERT INTO `article`( `sujet_x_id`, `titre`, `auteur`, `descripton`, `img_article`, `likes`, `dislikes`) VALUES (?,?,?,?,?,0,0)";
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, t.getSujet().getId());
            ps.setString(2, t.getTitre()); /// change integration animale id
            ps.setString(3, t.getAuteur());
            ps.setString(4, t.getDescription());
            ps.setString(5, t.getImg());

            boolean r = ps.executeUpdate() > 0;
            sendSMS("article avec le titre " + t.getTitre() + " a été ajouter", "+21652234709");
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean update(Article t) {
        String sql = "UPDATE `article` SET `sujet_x_id`=?,`titre`=?,`auteur`=?,`descripton`=?,`img_article`=? WHERE `id`=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, t.getSujet().getId());
            ps.setString(2, t.getTitre()); /// change integration animale id
            ps.setString(3, t.getAuteur());
            ps.setString(4, t.getDescription());
            ps.setString(5, t.getImg());

            ps.setInt(6, t.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Article t) {
        String sql = "DELETE FROM `article` WHERE id=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, t.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Article> getAll() {
        String sql = "SELECT * FROM `article` INNER JOIN sujet on sujet.id = article.sujet_x_id;";

        List<Article> list = new ArrayList<>();
        try {
            st = con.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                Sujet sujet = new Sujet();
                sujet.setId(rs.getInt("sujet_x_id"));

                sujet.setNom(rs.getString("nom"));
                sujet.setDescription(rs.getString("descr"));
                sujet.setImg(rs.getString("img_sujet"));

                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setDescription(rs.getString("descripton"));
                article.setImg(rs.getString("img_article"));
                article.setSujet(sujet);
                article.setTitre(rs.getString("titre"));
                article.setAuteur("auteur");
                article.setLike(rs.getInt("likes"));
                article.setDislike(rs.getInt("dislikes"));

                list.add(article);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Article getOne(int id) {
        String sql = "SELECT * FROM `article` INNER JOIN sujet on sujet.id = article.sujet_x_id where article.id=" + id;
        Article article = new Article();

        try {
            st = con.createStatement();

            rs = st.executeQuery(sql);

            rs.next();
            Sujet sujet = new Sujet();
            sujet.setId(rs.getInt("sujet_x_id"));

            sujet.setNom(rs.getString("nom"));
            sujet.setDescription(rs.getString("descr"));
            sujet.setImg(rs.getString("img_sujet"));

            article.setId(rs.getInt("id"));
            article.setDescription(rs.getString("descripton"));
            article.setImg(rs.getString("img_article"));
            article.setSujet(sujet);
            article.setTitre(rs.getString("titre"));
            article.setAuteur("auteur");
            article.setLike(rs.getInt("likes"));
            article.setDislike(rs.getInt("dislikes"));

        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return article;
    }

    public void sendSMS(String message, String num) {
        String accountSid = "ACaf8bf10a60dcea7dbfd05c2dabd18c35";
        String authToken = "63086eb67e4699dc0f340f516557fa5a";
        Twilio.init(accountSid, authToken);

        String from = "+16076012571"; // your Twilio phone number
        String to = "+21694567476"; // recipient phone number
        Message m = Message.creator(new PhoneNumber(to), new PhoneNumber(from), message).create();

        System.out.println(m.getSid()); // print the message SID to the console

    }

    public boolean updateLike(Article t) {
       String sql = "UPDATE article SET likes=likes+1 WHERE `id`=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, t.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateDislike(Article t) {
        String sql = "UPDATE article SET dislikes=dislikes+1 WHERE `id`=?";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, t.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ArticleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
