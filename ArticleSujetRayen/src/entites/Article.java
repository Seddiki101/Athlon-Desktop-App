/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author wasli rayen
 */
public class Article {

    private int id;
    private Sujet sujet;
    private String titre;
    String auteur;
    String description;
    String img;
    private int like;
    private int dislike;

    public Article() {
    }

    public Article(int id, Sujet sujet, String titre, String auteur, String description, String img) {
        this.id = id;
        this.sujet = sujet;
        this.titre = titre;
        this.auteur = auteur;
        this.description = description;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sujet getSujet() {
        return sujet;
    }

    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String importance) {
        this.img = img;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", sujet=" + sujet + ", titre=" + titre + ", auteur=" + auteur + ", description=" + description + ", img=" + img + '}';
    }

}
