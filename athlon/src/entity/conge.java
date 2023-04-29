package entity;

import java.util.Date;


public class conge {
    private int id;
    private int employe_id;
    private Date date_d;
    private Date date_f;
    private String type;

    public conge(int id, int employe_id, Date date_d, Date date_f, String type) {
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

    public Date getDateDebut() {
        return date_d;
    }

    public void setDateDebut(Date date_d) {
        this.date_d = date_d;
    }

    public Date getDateFin() {
        return date_f;
    }

    public void setDateFin(Date date_f) {
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
