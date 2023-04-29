package entity;

public class Exercices {
    private int id;
    private String nom;
    private int duree_exercices;
    private int nombre_repetitions;
    private String desc_exercices;
    private String machine;
    private int id_cours;

    
    public Exercices(int id, String nom, int duree_exercices, int nombre_repetitions, String desc_exercices, String machine, int id_cours) {
        this.id = id;
        this.nom = nom;
        this.duree_exercices = duree_exercices;
        this.nombre_repetitions = nombre_repetitions;
        this.desc_exercices = desc_exercices;
        this.machine = machine;
        this.id_cours = id_cours;
    }

    public Exercices(String nom, int duree_exercices, int nombre_repetitions, String desc_exercices, String machine, int id_cours) {
        this.nom = nom;
        this.duree_exercices = duree_exercices;
        this.nombre_repetitions = nombre_repetitions;
        this.desc_exercices = desc_exercices;
        this.machine = machine;
        this.id_cours = id_cours;
    }

    public Exercices() {
        // Une méthode vide
    }

   public Exercices(String nom, int duree_exercices, int nombre_repetitions, String desc_exercices, String machine) {
    this.nom = nom;
    this.duree_exercices = duree_exercices;
    this.nombre_repetitions = nombre_repetitions;
    this.desc_exercices = desc_exercices;
    this.machine = machine;
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

    public int getDuree_exercices() {
        return duree_exercices;
    }

    public void setDuree_exercices(int duree_exercices) {
        this.duree_exercices = duree_exercices;
    }

    public int getNombre_repetitions() {
        return nombre_repetitions;
    }

    public void setNombre_repetitions(int nombre_repetitions) {
        this.nombre_repetitions = nombre_repetitions;
    }

    public String getDesc_exercices() {
        return desc_exercices;
    }

    public void setDesc_exercices(String desc_exercices) {
        this.desc_exercices = desc_exercices;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public int getId_cours() {
        return id_cours;
    }

    public void setId_cours(int id_cours) {
        this.id_cours = id_cours;
    }

    @Override
    public String toString() {
        return "Exercices{" + "id=" + id + ", nom=" + nom + ", duree_exercices=" + duree_exercices + ", nombre_repetitions=" + nombre_repetitions + ", desc_exercices=" + desc_exercices + ", machine=" + machine + ", id_cours=" + id_cours + '}';
    }

   
}
