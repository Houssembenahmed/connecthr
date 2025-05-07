package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Produit;
import tn.esprit.services.ProduitService;

public class InterfaceAjoutProduitController {

    @FXML
    private Button AjouterProduitBtn;

    @FXML
    private TextField categorieProduitInput;

    @FXML
    private Button crmBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button invoiceBtn;

    @FXML
    private TextField nomProduitInput;

    @FXML
    private TextField prixProduitInput;

    @FXML
    private TextField quantiteProduitInput;

    @FXML
    private Button retourBtn;

    @FXML
    private Button staffBtn;

    @FXML
    private Button stockBtn;

    @FXML
    private Button suppliersBtn;

    // Assume a service class that contains the add method
    private ProduitService produitService = new ProduitService();

    @FXML
    void AjouterProduit(ActionEvent event) {
        // Retrieve input values
        String nom = nomProduitInput.getText().trim();
        String categorie = categorieProduitInput.getText().trim();
        String prixText = prixProduitInput.getText().trim();
        String quantiteText = quantiteProduitInput.getText().trim();

        // Validate inputs
        if (nom.isEmpty() || categorie.isEmpty() || prixText.isEmpty() || quantiteText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        float prix;
        int quantite;
        try {
            prix = Float.parseFloat(prixText);
            if (prix <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être positif.");
                return;
            }
            quantite = Integer.parseInt(quantiteText);
            if (quantite < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité ne peut pas être négative.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix et la quantité doivent être des nombres valides.");
            return;
        }

        // Create Produit object
        Produit produit = new Produit(prix, categorie, nom, quantite);

        // Call the add method
        boolean success = produitService.add(produit);

        // Provide feedback
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté avec succès.");
            // Clear input fields
            nomProduitInput.clear();
            categorieProduitInput.clear();
            prixProduitInput.clear();
            quantiteProduitInput.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout. Le produit existe déjà ou une erreur s'est produite.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String erreur, String s) {
    }

    @FXML
    void retourMain(ActionEvent event) {

    }

    @FXML
    void showCRM(ActionEvent event) {

    }

    @FXML
    void showDashboard(ActionEvent event) {

    }

    @FXML
    void showInvoices(ActionEvent event) {

    }

    @FXML
    void showStaff(ActionEvent event) {

    }

    @FXML
    void showStock(ActionEvent event) {

    }

    @FXML
    void showSuppliers(ActionEvent event) {

    }

}
