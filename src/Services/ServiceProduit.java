/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Produit;
import Entities.ProduitLike;
import Util.MyDB;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author msi
 */
public class ServiceProduit implements IService<Produit> {

    Statement Ste;
    Connection cnx;

    public ServiceProduit() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouterProduit(Produit t) {
        String qry = "INSERT INTO `produit`(`brand`, `description`, `prix`, `image`, `nom`, `categories_id`) VALUES ('" + t.getBrand() + "','" + t.getDescription() + "','" + t.getPrix() + "','" + t.getImage() + "','" + t.getNom() + "','" + t.getIdCategory() + "')";
        try {
            Statement stm = cnx.createStatement();

            stm.executeUpdate(qry);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*@Override
     public  void  supprimerProduit ( int  id ) {
       try {
            Statement stm=cnx.createStatement();
            String query="delete from produit where id = '"+id+"'";
           
            stm.executeUpdate(query);
            
       } catch (SQLException ex) {
           Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
       }
     }
     */
    @Override
    public List<Produit> afficherProduit() {
        List<Produit> produits = new ArrayList();
        //String qry ="SELECT * FROM `produit`";
        String qry = "SELECT p.id , p.brand,p.description,p.prix,p.image,p.nom,c.nom, (SELECT AVG(rating) from ratings where ratings.id_produit = p.id) as moyRating  FROM categorie c JOIN produit p ON c.id = p.categories_id;";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setBrand(rs.getString("brand"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));

                p.setImage(rs.getString("image"));
                p.setNom(rs.getString("nom"));
                p.setNomCategory(rs.getString(7));
                p.setMoyRating(rs.getFloat(8));

                produits.add(p);
            }

            return produits;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return produits;
    }

    public List<Produit> filterProduitsParPrix(float min, float max) {
         List<Produit> produits = new ArrayList();
        //String qry ="SELECT * FROM `produit`";
        String qry = "SELECT p.id , p.brand,p.description,p.prix,p.image,p.nom,c.nom  FROM categorie c JOIN produit p ON c.id = p.categories_id WHERE p.prix BETWEEN " + min + " AND " + max + ";";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setBrand(rs.getString("brand"));
                p.setDescription(rs.getString("description"));
                p.setPrix(rs.getFloat("prix"));

                p.setImage(rs.getString("image"));
                p.setNom(rs.getString("nom"));
                p.setNomCategory(rs.getString(7));

                produits.add(p);
            }

            return produits;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return produits;
    }


    /*
    @Override
    public void modifierProduit (int id, String nom, String brand,String description, int prix, String image){
         String requete="UPDATE produit SET nom='"+nom+"', brand='"+brand+"', description='"+description+"',prix='"+prix+"',image='"+image+"'  WHERE id='"+id+"'";
         
         
         try{
            
             Ste = cnx.createStatement();
            Ste.executeUpdate(requete);
            System.out.println("Produit modifié");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

   
     */

    @Override
    public boolean modifierProduit(Produit t) {
        try {
            // String qry = "UPDATE produit SET nom = '" + t.getNom()+ "',`category_id` = '" + t.getIdCategory()+ "', description = '" + t.getDescription() + "',`quantite` = '" + t.getQuantite() + "',`prix` = '" + t.getPrix() + "',`image` = '" + t.getImage() + "' WHERE id = '" + t.getId() + "'";
            String qry = "UPDATE `produit` SET `brand` = '" + t.getBrand() + "', `description` = '" + t.getDescription() + "',`prix` = '" + t.getPrix() + "', `image` = '" + t.getImage() + "', `nom` = '" + t.getNom() + "',`categories_id` = '" + t.getIdCategory() + "'  WHERE `id` = '" + t.getId() + "'";
            Statement stm = cnx.createStatement();

            stm.executeUpdate(qry);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public void supprimerProduit(Produit t) {
        String qry = "DELETE FROM produit WHERE id=?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(qry);
            pstmt.setInt(1, t.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    public boolean existeProduit(String nomProduit) {
    boolean existe = false;
    // Effectuer une requête à la base de données pour vérifier si le produit existe déjà
    // Ici, on suppose que la requête retourne un boolean indiquant si le produit existe ou non
    // par exemple, en utilisant une requête SQL SELECT COUNT(*) FROM produits WHERE nom = 'nomProduit';
    // et en vérifiant si le résultat est > 0
    
    // exemple de code pour une base de données MySQL
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Athlon");
         PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM produit WHERE nom = ?")) {
        stmt.setString(1, nomProduit);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            if (count > 0) {
                existe = true;
            }
        }
    } catch (SQLException ex) {
        // Gérer les exceptions
        ex.printStackTrace();
    }

    return existe;
}*/
    public List<Produit> getProduitByCategory(String topCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private List<Produit> produits;

    public List<ProduitLike> likes(int id) {
        List<ProduitLike> produits = new ArrayList<>();
        try {
            String req = "select * from produit_like where produit_id =" + id + ";";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                ProduitLike p = new ProduitLike();
                p.setId(rs.getInt("id"));
                p.setIdproduit(rs.getInt("produit_id"));
                //p.setIduser(rs.getInt("user_id"));
                produits.add(p);
            }
            System.out.print(produits);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }

    public List<ProduitLike> islikedbyuser(int idp) {

        List<ProduitLike> produits = new ArrayList<>();
        try {
            String req = "Select * from produit_like where produit_id= '" + idp + "';";

            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                ProduitLike p = new ProduitLike();
                p.setId(rs.getInt("id"));
                p.setIdproduit(rs.getInt("produit_id"));
                produits.add(p);
            }
            System.out.println(produits.size());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }

    public void Supprimerlike(int id) {
        try {
            Statement st = cnx.createStatement();
            String req = "DELETE FROM produit_like WHERE produit_id = '" + id + "';";
            st.executeUpdate(req);
            System.out.println("produit supprimer avec succès...");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean ajouterlike(int idp) {
        boolean a = false;
        try {
            String req = "insert into produit_like(produit_id) values(" + idp + ")";

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            //System.out.println("like ajoutée");
            a = true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return a;
    }

}
