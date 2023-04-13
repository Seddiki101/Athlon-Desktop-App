/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Timestamp;

/**
 *
 * @author Seif Boulabiar
 */
public class Order {

    private int id;
    private User user;  //USER
    private String state;
    private Timestamp date;

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
