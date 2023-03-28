/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author k
 */
public class Reclamation {
    int id;
    int usrId;
    String titre;
    String desc;

    public Reclamation() {
    }

    public Reclamation(int id, String titre, String desc) {
        this.id = id;
        this.titre = titre;
        this.desc = desc;
    }

    
    
    public Reclamation(int id, int usrId, String titre, String desc) {
        this.id = id;
        this.usrId = usrId;
        this.titre = titre;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", titre=" + titre + ", desc=" + desc + '}';
    }
    
    
    
    
}
