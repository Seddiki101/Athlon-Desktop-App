/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author Houssem Charef
 */
public class OrderItem {

    private int id;
    private Order order; //ORDER
    private Produit produit;//PRODUCT
    private int quantity;

    public OrderItem(int id, Order order, Produit produit, int quantity) {
        this.id = id;
        this.order = order;
        this.produit = produit;
        this.quantity = quantity;
    }

    public OrderItem(Order order, Produit produit, int quantity) {
        this.order = order;
        this.produit = produit;
        this.quantity = quantity;
    }

    public OrderItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Produit getProduct() {
        return produit;
    }

    public void setProduct(Produit produit) {
        this.produit = produit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OderItem{"
                + "id=" + id
                + ", orderId=" + order
                + ", productId=" + produit
                + ", quantity=" + quantity
                + '}';
    }

}
