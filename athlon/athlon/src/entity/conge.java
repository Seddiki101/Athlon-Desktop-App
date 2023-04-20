package entity;

import java.util.Date;


public class conge {
    private int id;
    private int employe_id;
    private String date_d;
    private String date_f;
    private String type;

    public conge(int id, int employe_id, String date_d, String date_f, String type) {
        this.id = id;
        this.employe_id = employe_id;
        this.date_d = date_d;
        this.date_f = date_f;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeId() {
        return employe_id;
    }

 public void setEmployeId(int employe_id) {
    this.employe_id = employe_id;
}

    public String getDateDebut() {
        return date_d;
    }

    public void setDateDebut(String date_d) {
        this.date_d = date_d;
    }

    public String getDateFin() {
        return date_f;
    }

    public void setDateFin(String date_f) {
        this.date_f = date_f;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Conge{" +
                "id=" + id +
                ", employe_id=" + employe_id +
                ", date_d=" + date_d +
                ", date_f=" + date_f +
                ", type='" + type + '\'' +
                '}';
    }
}
