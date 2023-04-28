/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Seif Boulabiar
 */
public class Order {

    private int id;
    private User user;  //USER
    private String state;
    private int remise;
    private Timestamp date;
   
//gl
    public static List<OrderItem> orderItems = new ArrayList<>();

    public Order(int id) {
        this.id = id;
    }

    public Order(int id, User user, String state, Timestamp date) {
        this.id = id;
        this.user = user;
        this.state = state;
        this.date = date;
    }

    public Order(User user, String state) {
        this.user = user;
        this.state = state;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getRemise() {
        return remise;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public static float getTotale() {
        return (float) orderItems.stream().mapToDouble(or -> or.getProduct().getPrix() * or.getQuantity()).sum();
    }

    
    //ajout panier
    
    public static void addproduct(OrderItem oi) {
        //produit dispo ou nn
        boolean t = orderItems.stream().anyMatch(or -> oi.getProduct().getId() == or.getProduct().getId());
        if (t) {
            OrderItem o = orderItems.stream().filter(or -> oi.getProduct().getId() == or.getProduct().getId()).findFirst().get();
            System.out.println(o);

            orderItems.get(orderItems.indexOf(o)).setQuantity(orderItems.get(orderItems.indexOf(o)).getQuantity() + 1);

        } else {
            oi.setQuantity(1);
            orderItems.add(oi);
        }
    }

    public static void remove(OrderItem oi) {
        orderItems.remove(oi);
    }

    public static void removeOne(OrderItem oi) {
        OrderItem o = orderItems.stream().filter(or -> oi.getProduct().getId() == or.getProduct().getId()).findFirst().get();
        orderItems.get(orderItems.indexOf(o)).setQuantity(orderItems.get(orderItems.indexOf(o)).getQuantity() - 1);

    }

    public static List<OrderItem> getOrderItems() {
//        OrderItem oi = new OrderItem();
//        oi.setQuantity(3);
//        Produit p = new Produit();
//        p.setMarque("marque");
//        p.setPrix(10);
//        oi.setProduct(p);
//        orderItems.add(oi);
        return orderItems;
    }

    public static void setOrderItems(List<OrderItem> orderItems) {
        Order.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", user=" + user
                + ", state='" + state + '\''
                + ", date=" + date
                + '}';
    }

}
