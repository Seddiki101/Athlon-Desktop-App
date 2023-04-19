package edu.esprit.services;

import edu.esprit.entities.Cours;
import edu.esprit.entities.Exercices;
import edu.esprit.utils.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceExercices {
    private Connection con = MyConnection.getInstance().getConnection();



    public void ajouterEX(Exercices exercices) throws SQLException {
        String query = "INSERT INTO exercices (cours_id, nom, duree_exercices, nombre_repetitions, desc_exercices, machine) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, exercices.getId_cours());
        stmt.setString(2, exercices.getNom());
        stmt.setInt(3, exercices.getDuree_exercices());
        stmt.setInt(4, exercices.getNombre_repetitions());
        stmt.setString(5, exercices.getDesc_exercices());
        stmt.setString(6, exercices.getMachine());
        stmt.executeUpdate();
    }


    public void updateEX(Exercices exercices) throws SQLException {
        String query = "UPDATE exercices SET cours_id = ?, nom = ?, duree_exercices = ?, " +
                       "nombre_repetitions = ?, desc_exercices = ?, machine = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
       stmt.setInt(1, exercices.getId_cours());
        stmt.setString(2, exercices.getNom());
        stmt.setInt(3, exercices.getDuree_exercices());
        stmt.setInt(4, exercices.getNombre_repetitions());
        stmt.setString(5, exercices.getDesc_exercices());
        stmt.setString(6, exercices.getMachine());
        stmt.setInt(7, exercices.getId());

        stmt.executeUpdate();
    }

    public void deleteEX(int id) throws SQLException {
        String query = "DELETE FROM exercices WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

    public ArrayList<Exercices> AfficherEX() throws SQLException {
    ArrayList<Exercices> exerciceslist = new ArrayList<>();
    String query = "SELECT * FROM exercices";
    PreparedStatement stmt = con.prepareStatement(query);
    ResultSet rs = stmt.executeQuery();

    while (rs.next()) {
        Exercices exercices = new Exercices();
        exercices.setId(rs.getInt("id"));
        exercices.setId_cours(rs.getInt("cours_id"));
        exercices.setNom(rs.getString("nom"));
        exercices.setDuree_exercices(rs.getInt("duree_exercices"));
        exercices.setNombre_repetitions(rs.getInt("nombre_repetitions"));
        exercices.setDesc_exercices(rs.getString("desc_exercices"));
        exercices.setMachine(rs.getString("machine"));
        exerciceslist.add(exercices);
    }

    return exerciceslist;
}

        
    
    }




