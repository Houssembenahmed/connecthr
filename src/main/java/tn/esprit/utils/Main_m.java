package tn.esprit.utils;

import tn.esprit.models.mouvements;
import tn.esprit.services.mouvementservice;

import java.util.Date;
import java.util.List;

public class Main_m {
    public static void main(String[] args) {
        mouvementservice mouvementService = new mouvementservice();

        // 🔹 Création d’un mouvement d’ENTREE
        mouvements entree = new mouvements();
        entree.setId_produit(15);
        entree.setType_mouv(mouvements.TypeMouv.ENTREE);
        entree.setQuantite_mouv(50);
        entree.setDate_mouv(new java.sql.Date(new Date().getTime()));
        if (mouvementService.add(entree)) {
            System.out.println("Mouvement d'entrée ajouté !");
        }

        // 🔹 Création d’un mouvement de SORTIE
        mouvements sortie = new mouvements();
        sortie.setId_produit(15);
        sortie.setType_mouv(mouvements.TypeMouv.SORTIE);
        sortie.setQuantite_mouv(20);
        sortie.setDate_mouv(new java.sql.Date(new Date().getTime()));
        if (mouvementService.add(sortie)) {
            System.out.println("Mouvement de sortie ajouté !");
        }

        // 🔹 Récupérer tous les mouvements
        List<mouvements> mouvements = mouvementService.getAll();
        System.out.println("\n Liste des mouvements :");
        for (mouvements m : mouvements) {
            System.out.println(m);
        }

        // 🔹 Récupérer un mouvement par ID (par exemple id=1)
        mouvements m1 = mouvementService.getById(7);
        if (m1 != null) {
            System.out.println("\n Mouvement avec ID=1 : " + m1);
        } else {
            System.out.println("\nAucun mouvement trouvé avec ID=1.");
        }

        // 🔹 Supprimer un mouvement par ID (par exemple id=2)
        boolean deleted = mouvementService.delete(9);
        if (deleted) {
            System.out.println(" Mouvement avec ID=2 supprimé.");
        } else {
            System.out.println(" Échec de la suppression.");
        }
    }
}
