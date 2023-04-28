/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.*;
import utils.DataSource;

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
public class OrderItemService implements IService<OrderItem> {

    private Connection cnx;
    private PreparedStatement pst;

    public OrderItemService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public boolean insert(OrderItem item) {
        if (!getAll().contains(item)) { // if it doesn't exist then insert it
            String req = "insert into commande_item (commande,produit,quantity) values (?,?,?)";
            try {
                pst = cnx.prepareStatement(req);
                pst.setInt(1, item.getOrder().getId());
                pst.setInt(2, 1);//item.getProduct().getId()
                pst.setInt(3, item.getQuantity());
                pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            return true;
        }
        return update(item); // else update quantity
    }

    @Override
    public boolean update(OrderItem item) {
        String req = "update commande_item set quantity=?,`commande`=? where id=?";
        try {

            pst = cnx.prepareStatement(req);
            pst.setInt(1, item.getQuantity());
            pst.setInt(2, item.getOrder().getId());
            pst.setInt(3, item.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(OrderItem item) {
        String req = "delete from commande_item where id=?"; //remove product from cart
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, item.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public List<OrderItem> getAll() {
        String req = "select * from commande_item";
        List<OrderItem> itemsList = new ArrayList<>();

        try {
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                itemsList.add(new OrderItem(rs.getInt("id"), new OrderService().getOne(rs.getInt("commande")), new Produit(rs.getInt("produit"), "test"), rs.getInt("quantity"))); //new ServiceProduit().getOne(rs.getInt(3))
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemsList;
    }

    @Override
    public OrderItem getOne(int id) {
        String req = "select * from commande_item where id=?";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                OrderItem item = new OrderItem(rs.getInt(1), new OrderService().getOne(rs.getInt(2)), new Produit(rs.getInt(3), "test"), rs.getInt(4)); //new ServiceProduit().getOne(rs.getInt(3))
                return item;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public List<Produit> produit() {
        String req = "select * from produit";
        List<Produit> ordersList = new ArrayList<>();
        try {
            pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id_p"));
                p.setMarque(rs.getString("brand"));
                p.setPrix(rs.getFloat("prix"));
                ordersList.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordersList;
    }
}
