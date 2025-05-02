package tn.esprit;

import tn.esprit.models.produits;
import tn.esprit.services.produitservice;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        produitservice ps = new produitservice();

        // Créer un nouveau produit (sans spécifier l'id, car il est auto-incrémenté)
        produits nouveauProduit = new produits( 10, "Électronique", "huawei", 20);
        boolean isAdded = ps.add(nouveauProduit);
        System.out.println("Produit ajouté ? " + isAdded);

        // Afficher tous les produits
        System.out.println("\nListe des produits :");
        List<produits> produitsList = ps.getAll();
        for (produits p : produitsList) {
            System.out.println(p);
        }

        // Supposons qu'on veut mettre à jour le premier produit récupéré
        if (!produitsList.isEmpty()) {
            produits prodToUpdate = produitsList.get(0);
            prodToUpdate.setNom_prod("Smartphone Pro");
            prodToUpdate.setPrix_prod(1099.99f);
            prodToUpdate.setQuantite_stockee(15);
            boolean isUpdated = ps.update(prodToUpdate);
            System.out.println("\nProduit mis à jour ? " + isUpdated);

            // Vérification de la quantité disponible
            boolean dispo = ps.verifierQuantiteDisponible(prodToUpdate.getId_prod(), 10);
            System.out.println("Quantité suffisante pour 10 unités ? " + dispo);

            // Retrait de quantité
            boolean retrait = ps.retirerQuantite(prodToUpdate.getId_prod(), 5);
            System.out.println("Retrait de 5 unités effectué ? " + retrait);

            // Affichage du produit mis à jour
            produits updated = ps.getById(prodToUpdate.getId_prod());
            System.out.println("Produit après mise à jour : " + updated);

            // Suppression du produit
            boolean isDeleted = ps.delete(prodToUpdate);
            System.out.println("Produit supprimé ? " + isDeleted);
        } else {
            System.out.println("Aucun produit à mettre à jour ou supprimer.");
        }
    }
}
