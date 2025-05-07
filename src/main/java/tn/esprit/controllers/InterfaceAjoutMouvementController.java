package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.models.Mouvement;
import tn.esprit.models.Produit;
import tn.esprit.services.MouvementService;
import tn.esprit.services.ProduitService;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class InterfaceAjoutMouvementController implements Initializable {

    @FXML
    private Button AjouterMouvementBtn;

    @FXML
    private ComboBox<String> categorieCombo;

    @FXML
    private Button crmBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private DatePicker dateMouvementPicker;

    @FXML
    private Button invoiceBtn;

    @FXML
    private ComboBox<String> produitCombo;

    @FXML
    private TextField quantiteMouvementInput;

    @FXML
    private Button retourBtn;

    @FXML
    private Button staffBtn;

    @FXML
    private Button stockBtn;

    @FXML
    private Button suppliersBtn;

    @FXML
    private ComboBox<Mouvement.TypeMouv> typeMouvementCombo;

    // Service classes for database operations
    private ProduitService produitService = new ProduitService();
    private MouvementService mouvementService = new MouvementService();

    // Map to store product names to Produit objects
    private Map<String, Produit> produitMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate typeMouvementCombo with ENUM values
        typeMouvementCombo.getItems().addAll(Mouvement.TypeMouv.values());

        // Populate categorieCombo with available categories
        List<String> categories = produitService.getCategories();
        categorieCombo.getItems().addAll(categories);

        // Set up listener for categorieCombo to update produitCombo
        categorieCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            produitCombo.getItems().clear();
            produitMap.clear();
            if (newValue != null) {
                List<String> productNames = produitService.getProductsName(newValue);
                for (String productName : productNames) {
                    Produit produit = produitService.getByNomAndCategorie(productName, newValue);
                    if (produit != null) {
                        produitCombo.getItems().add(productName);
                        produitMap.put(productName, produit);
                    }
                }
            }
        });
    }

    @FXML
    void AjouterMouvement(ActionEvent event) {
        // Retrieve input values
        String categorie = categorieCombo.getValue();
        String produitNom = produitCombo.getValue();
        Mouvement.TypeMouv typeMouvement = typeMouvementCombo.getValue();
        String quantiteText = quantiteMouvementInput.getText().trim();
        LocalDate dateMouvement = dateMouvementPicker.getValue();

        // Validate inputs
        if (categorie == null || produitNom == null || typeMouvement == null || quantiteText.isEmpty() || dateMouvement == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Retrieve Produit from map
        Produit produit = produitMap.get(produitNom);
        if (produit == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Produit non trouvé.");
            return;
        }

        int quantite;
        try {
            quantite = Integer.parseInt(quantiteText);
            if (quantite <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité doit être positive.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "La quantité doit être un nombre valide.");
            return;
        }

        // Convert LocalDate to java.util.Date
        Date date = java.sql.Date.valueOf(dateMouvement);

        // Create Mouvement object
        Mouvement mouvement = new Mouvement();
        mouvement.setId_produit(produit.getId_prod());
        mouvement.setType_mouv(typeMouvement);
        mouvement.setQuantite_mouv(quantite);
        mouvement.setDate_mouv(date);

        // Add movement to database
        boolean success = mouvementService.add(mouvement);

        // Provide feedback
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Mouvement ajouté avec succès.");
            // Clear input fields
            categorieCombo.getSelectionModel().clearSelection();
            produitCombo.getItems().clear();
            produitMap.clear();
            typeMouvementCombo.getSelectionModel().clearSelection();
            quantiteMouvementInput.clear();
            dateMouvementPicker.setValue(null);
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du mouvement. Vérifiez la disponibilité du stock pour les sorties.");
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void retourMain(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void showCRM(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void showDashboard(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void showInvoices(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void showStaff(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void showStock(ActionEvent event) {
        // To be implemented
    }

    @FXML
    void showSuppliers(ActionEvent event) {
        // To be implemented
    }
}