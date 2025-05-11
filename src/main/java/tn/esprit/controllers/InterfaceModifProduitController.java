package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import tn.esprit.models.Produit;
import tn.esprit.services.ProduitService;

public class InterfaceModifProduitController {

    @FXML
    private VBox contentArea;

    @FXML
    private Button crmBtn;

    @FXML
    private Label currentCategorieProduit;

    @FXML
    private Label currentNomProduit;

    @FXML
    private Label currentPrixProduit;

    @FXML
    private Label currentQuantiteProduit;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button invoiceBtn;

    @FXML
    private Button modifierBtn;

    @FXML
    private TextField newCategorieProduitInput;

    @FXML
    private TextField newNomProduitInput;

    @FXML
    private TextField newPrixProduitInput;

    @FXML
    private TextField newQuantiteProduitInput;

    @FXML
    private Button retourBtn;

    @FXML
    private TextField searchCategorieProduitInput;

    @FXML
    private TextField searchNomProduitInput;

    @FXML
    private Button searchProduitBtn;

    @FXML
    private Button staffBtn;

    @FXML
    private Button stockBtn;

    @FXML
    private Button suppliersBtn;

    private ProduitService produitService = new ProduitService();

    private int currentProduitId = -1;

    @FXML
    void searchProduit(ActionEvent event) {
        String nom = searchNomProduitInput.getText().trim();
        String categorie = searchCategorieProduitInput.getText().trim();

        if (nom.isEmpty() && categorie.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer au moins un critère de recherche (nom ou catégorie).");
            return;
        }

        Produit produit = produitService.getByNomAndCategorie(nom.isEmpty() ? null : nom, categorie.isEmpty() ? null : categorie);

        if (produit != null) {
            currentNomProduit.setText(produit.getNom_prod());
            currentCategorieProduit.setText(produit.getCategorie_prod());
            currentPrixProduit.setText(String.valueOf(produit.getPrix_prod()));
            currentQuantiteProduit.setText(String.valueOf(produit.getQuantite_stockee()));
            currentProduitId = produit.getId_prod();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit trouvé.");
        } else {
            currentNomProduit.setText("N/A");
            currentCategorieProduit.setText("N/A");
            currentPrixProduit.setText("N/A");
            currentQuantiteProduit.setText("N/A");
            currentProduitId = -1;
            showAlert(Alert.AlertType.WARNING, "Aucun résultat", "Aucun produit trouvé avec ces critères.");
        }
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        if (currentProduitId == -1) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez d'abord rechercher un produit à modifier.");
            return;
        }

        String newNom = newNomProduitInput.getText().trim();
        String newCategorie = newCategorieProduitInput.getText().trim();
        String newPrixText = newPrixProduitInput.getText().trim();
        String newQuantiteText = newQuantiteProduitInput.getText().trim();

        if (newNom.isEmpty() || newCategorie.isEmpty() || newPrixText.isEmpty() || newQuantiteText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        float newPrix;
        int newQuantite;
        try {
            newPrix = Float.parseFloat(newPrixText);
            if (newPrix <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être positif.");
                return;
            }
            newQuantite = Integer.parseInt(newQuantiteText);
            if (newQuantite < 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité ne peut pas être négative.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix et la quantité doivent être des nombres valides.");
            return;
        }

        Produit updatedProduit = new Produit();
        updatedProduit.setId_prod(currentProduitId);
        updatedProduit.setNom_prod(newNom);
        updatedProduit.setCategorie_prod(newCategorie);
        updatedProduit.setPrix_prod(newPrix);
        updatedProduit.setQuantite_stockee(newQuantite);

        boolean success = produitService.update(updatedProduit);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit modifié avec succès.");
            currentNomProduit.setText(newNom);
            currentCategorieProduit.setText(newCategorie);
            currentPrixProduit.setText(String.valueOf(newPrix));
            currentQuantiteProduit.setText(String.valueOf(newQuantite));

            newNomProduitInput.clear();
            newCategorieProduitInput.clear();
            newPrixProduitInput.clear();
            newQuantiteProduitInput.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la modification du produit.");
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

    public void setProduit(Produit produit) {

    }
}
