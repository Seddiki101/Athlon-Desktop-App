/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewCour.entities;

import javafx.beans.property.SimpleIntegerProperty;

public class Cours {
    private int id;
    private String nom;
    private String description_cours;
    private String niveau_cours;
    private int capacity;
    private int duree_cours;
    private SimpleIntegerProperty capacityProperty;

public SimpleIntegerProperty capacityProperty() {
    if (capacityProperty == null) {
        capacityProperty = new SimpleIntegerProperty(capacity);
    }
    return capacityProperty;
}

    public Cours() {
    }

    public Cours(String nom, String description_cours, String niveau_cours, int capacity, int duree_cours) {
        this.nom = nom;
        this.description_cours = description_cours;
        this.niveau_cours = niveau_cours;
        this.capacity = capacity;
        this.duree_cours = duree_cours;
        
    }

    public Cours(int id, String nom, String description_cours, String niveau_cours, int capacity, int duree_cours) {
        this.id = id;
        this.nom = nom;
        this.description_cours = description_cours;
        this.niveau_cours = niveau_cours;
        this.capacity = capacity;
        this.duree_cours = duree_cours;
    }

    public Cours(String nom, String description_cours, int duree_cours, int capacity, String niveau_cours) {
    this.nom = nom;
    this.description_cours = description_cours;
    this.niveau_cours = niveau_cours;
    this.capacity = capacity;
    this.duree_cours = duree_cours;
}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription_cours() {
        return description_cours;
    }

    public void setDescription_cours(String description_cours) {
        this.description_cours = description_cours;
    }

    public String getNiveau_cours() {
        return niveau_cours;
    }

    public void setNiveau_cours(String niveau_cours) {
        this.niveau_cours = niveau_cours;
    }

    public int getCapacity() {
    return capacityProperty().get();
}
    public void setCapacity(int capacity) {
    capacityProperty().set(capacity);
}
    public int getDuree_cours() {
        return duree_cours;
    }

    public void setDuree_cours(int duree_cours) {
        this.duree_cours = duree_cours;
    }

   
    @Override
    public String toString() {
        return "Cours{" + "id=" + id + ", nom=" + nom + ", description_cours=" + description_cours + ", niveau_cours=" + niveau_cours + ", capacity=" + capacity + ", duree_cours=" + duree_cours + '}';
    }
}
