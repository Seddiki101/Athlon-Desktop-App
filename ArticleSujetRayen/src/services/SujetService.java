/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entites.Sujet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author wasli rayen
 */
public class SujetService implements IService<Sujet> {
    
    private Connection con;
    private Statement st;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public SujetService() {
        con = DataSource.getInstance().getCnx();
    }
    
    @Override
    public boolean insert(Sujet t) {
        String sql = "INSERT INTO `sujet`( `nom`, `descr`, `img_sujet`) VALUES (?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, t.getNom());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getImg());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean update(Sujet t) {
        String sql = "UPDATE `sujet` SET `nom`=?,`descr`=?,`img_sujet`=? WHERE `id`=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setString(1, t.getNom());
            ps.setString(2, t.getDescription());
            ps.setString(3, t.getImg());
            ps.setInt(4, t.getId());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public boolean delete(Sujet t) {
        String sql = "DELETE FROM `sujet` WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, t.getId());
            return ps.executeUpdate() > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @Override
    public List<Sujet> getAll() {
        String sql = "SELECT * FROM `sujet`";
        
        List<Sujet> list = new ArrayList<>();
        try {
            st = con.createStatement();
            
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Sujet sujet = new Sujet();
                sujet.setId(rs.getInt("id"));
                
                sujet.setNom(rs.getString("nom"));
                sujet.setDescription(rs.getString("descr"));
                sujet.setImg(rs.getString("img_sujet"));
                
                list.add(sujet);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    @Override
    public Sujet getOne(int id) {
        String sql = "SELECT * FROM `sujet` where id=" + id;
        Sujet sujet = new Sujet();
        
        try {
            st = con.createStatement();
            
            rs = st.executeQuery(sql);
            
            rs.next();
            sujet.setId(rs.getInt("id"));
            sujet.setNom(rs.getString("nom"));
            sujet.setDescription(rs.getString("descr"));
            sujet.setImg(rs.getString("img_sujet"));
            
        } catch (SQLException ex) {
            Logger.getLogger(SujetService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sujet;
    }
    
}
