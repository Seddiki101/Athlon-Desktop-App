/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entity.Order;
import entity.Produit;
import entity.User;
import util.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seif Boulabiar
 */
public class OrderService implements IIService<Order> {

    private Connection cnx;
    private PreparedStatement pst;

    public OrderService() {
        cnx = ConnectionDB.getInstance().getCnx();
    }

    public int insertID(Order order) {
        String generatedColumns[] = {"ID"};

        String sql = "insert into commande (statue,date,user,remise) values (?,?,?,?)";

        try {
            PreparedStatement ps = cnx.prepareStatement(sql, generatedColumns);

            ps.setString(1, order.getState());
            ps.setTimestamp(2, order.getDate());
            ps.setInt(3, 1);// current user
            ps.setInt(4, order.getRemise());
            int affectedRows = ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    @Override
    public boolean insert(Order order) {
        String req = "insert into commande (statue,date) values (?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, order.getState());// current user
            pst.setTimestamp(2, order.getDate());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Order order) {
        String req = "update commande set statue=? where id_c=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, order.getState());
            pst.setInt(2, order.getId());
            pst.executeUpdate();
            System.out.println("Record updated");

        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Order order) {
        String req = "delete from commande where id_c=?"; //delete order
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, order.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public List<Order> getAll() {
        String req = "select * from commande";
        List<Order> ordersList = new ArrayList<>();
        try {
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ordersList.add(new Order(rs.getInt("id_c"), new User(1), rs.getString("statue"), rs.getTimestamp("date"))); //current user
            }

            System.out.println(ordersList);

        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersList;
    }

    @Override
    public Order getOne(int id) {
        String req = "select * from commande where id_c=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Order order = new Order(rs.getInt("id_c"), new User(1), rs.getString("statue"), rs.getTimestamp("date"));
                return order;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
