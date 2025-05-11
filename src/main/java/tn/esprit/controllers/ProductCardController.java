package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Produit;
import tn.esprit.services.ProduitService;
import tn.esprit.utils.SceneLoader;

import java.io.IOException;

public class ProductCardController {

    @FXML
    private HBox cardProduit;

    @FXML
    private Label categorieProduit;

    @FXML
    private Button modifierButton;

    @FXML
    private ImageView modifierIcon;

    @FXML
    private Label nomProduit;

    @FXML
    private Label prixProduit;

    @FXML
    private Label quantiteStockee;

    @FXML
    private Button supprimerButton;

    @FXML
    private Label idprod;

    @FXML
    private ImageView supprimerIcon;

    private Produit produit;

    public void setProduit(Produit produit) {
        this.produit = produit;
        updateCard();
    }

    @FXML
    private void initialize() {
            idprod.setVisible(false);
    }

    private void updateCard() {
        if (produit != null) {
            nomProduit.setText(produit.getNom_prod());
            categorieProduit.setText(produit.getCategorie_prod());
            prixProduit.setText(String.format("Prix: %.2f DT", produit.getPrix_prod()));
            quantiteStockee.setText("Quantité en stock: " + produit.getQuantite_stockee());
        }
    }

    public void setData(Produit produit) {
        this.produit = produit;
        if (produit != null) {
            nomProduit.setText(produit.getNom_prod());
            categorieProduit.setText(produit.getCategorie_prod());
            prixProduit.setText(String.format("Prix: %.2f DT", produit.getPrix_prod()));
            quantiteStockee.setText("Quantité en stock: " + produit.getQuantite_stockee());

        }
    }

    private void handleModifierAction() {
        System.out.println("Modifier clicked for product: " + (produit != null ? produit.getNom_prod() : "null"));
    }

    private void handleSupprimerAction() {
        System.out.println("Supprimer clicked for product: " + (produit != null ? produit.getNom_prod() : "null"));
    }

    public void modifierProduit(ActionEvent actionEvent) {
        Stage stage = (Stage) modifierButton.getScene().getWindow();
        Scene scene = SceneLoader.load("/IntefaceModifProduitController.fxml");
        if (scene != null) {
            stage.setScene(scene);
        }
    }

    public void supprimerProduit(ActionEvent actionEvent) {

    }
}