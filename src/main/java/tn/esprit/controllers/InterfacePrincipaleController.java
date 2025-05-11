package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Produit;
import tn.esprit.services.ProduitService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InterfacePrincipaleController implements Initializable {

    @FXML
    private FlowPane cardlayouuts;

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

    private List<Produit> produitList;
    private Produit selectedProduit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllProduits();
    }

    private void loadAllProduits() {
        cardlayouuts.getChildren().clear();
        produitList = produitlist();

        for (Produit produit : produitList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CardProduit.fxml"));
                HBox cardBox = fxmlLoader.load();
                ProductCardController controller = fxmlLoader.getController();
                controller.setData(produit);
                cardlayouuts.getChildren().add(cardBox);
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement des cartes produit : " + e.getMessage());
            }
        }
    }

    public void selectProduit(Produit produit) {



        this.selectedProduit = produit;
        if (produit != null) {
            currentNomProduit.setText(produit.getNom_prod());
            currentCategorieProduit.setText(produit.getCategorie_prod());
            currentPrixProduit.setText(String.format("%.2f DT", produit.getPrix_prod()));
            currentQuantiteProduit.setText(String.valueOf(produit.getQuantite_stockee()));

            newNomProduitInput.setText(produit.getNom_prod());
            newCategorieProduitInput.setText(produit.getCategorie_prod());
            newPrixProduitInput.setText(String.valueOf(produit.getPrix_prod()));
            newQuantiteProduitInput.setText(String.valueOf(produit.getQuantite_stockee()));
        }
    }

    private List<Produit> produitlist() {
        ProduitService produitService = new ProduitService();
        return produitService.getAll();
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        System.out.println("Modifier produit: " + (selectedProduit != null ? selectedProduit.getNom_prod() : "null"));
    }

    @FXML
    void retourMain(ActionEvent event) {
        System.out.println("Retour clicked");
    }

    @FXML
    void searchProduit(ActionEvent event) {
        System.out.println("Search clicked");
    }

    @FXML
    void showCRM(ActionEvent event) {
        System.out.println("Show CRM");
    }

    @FXML
    void showDashboard(ActionEvent event) {
        System.out.println("Show Dashboard");
    }

    @FXML
    void showInvoices(ActionEvent event) {
        System.out.println("Show Invoices");
    }

    @FXML
    void showStaff(ActionEvent event) {
        System.out.println("Show Staff");
    }

    @FXML
    void showStock(ActionEvent event) {
        System.out.println("Show Stock");
    }

    @FXML
    void showSuppliers(ActionEvent event) {
        System.out.println("Show Suppliers");
    }

    public void ajouterProduit(ActionEvent actionEvent) {
        selectedProduit.getId_prod();
        ProduitService produitService = new ProduitService();
        produitService.update(selectedProduit);

    }

    public void supprimerProduit(ActionEvent actionEvent) {
        selectedProduit.getId_prod();
        ProduitService produitService = new ProduitService();
        produitService.delete(selectedProduit);
    }
}