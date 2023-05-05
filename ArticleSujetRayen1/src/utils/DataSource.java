/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private String url = "jdbc:mysql://localhost:3306/athlondb";
    private String user = "root";
    private String password = "";
    private Connection cnx;
    private static DataSource instance;

    private DataSource() {

        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}
