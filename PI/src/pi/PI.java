/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import Entities.Produit;
import Entities.Categorie;
import Services.ServiceProduit;
import Services.ServiceCategorie;
import Util.MyDB;
/**
 *
 * @author msi
 */
public class PI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Produit p5 = new  Produit("tapis","nike", "klm", "tapis.png",76);
      
       ServiceProduit sp = new ServiceProduit();
       
      // sp.ajouterProduit(p5);
      sp.supprimerProduit(p5);
  // sp.modifierProduit(p5);
     
         System.out.println(sp.afficherProduit());
        
         Categorie c1 = new  Categorie("accessoires","nike.png");
         ServiceCategorie ca = new ServiceCategorie();
         // ca.ajouterCategorie(c1);
         // ca.modifierCategorie(21, "rim", "image.png");
         // System.out.println(ca.afficherCategorie());
         //ca.supprimerCategorie(20);
    }
    
}
