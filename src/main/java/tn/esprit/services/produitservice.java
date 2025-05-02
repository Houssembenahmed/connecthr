package tn.esprit.services;
import tn.esprit.interfaces.Icrud;
import tn.esprit.models.produits;
import tn.esprit.utils.connecthrDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class produitservice implements Icrud<produits> {
    private Connection cnx  ;
    public produitservice(){
        cnx = connecthrDB.getInstance().getCnx();
    }

    @Override
    public boolean add(produits p) {
        String qry = "INSERT INTO  produits ( prix_prod, categorie_prod, nom_prod, quantite_stockee) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setFloat(1, p.getPrix_prod());
            pstm.setString(2, p.getCategorie_prod());
            pstm.setString(3, p.getNom_prod());
            pstm.setInt(4, p.getQuantite_stockee());

            pstm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<produits> getAll() {
        List<produits> produitsList = new ArrayList<>();
        String qry = "SELECT * FROM produits";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                produits p = new produits();
                p.setId_prod(rs.getInt("id_prod"));
                p.setPrix_prod(rs.getFloat("prix_prod"));
                p.setCategorie_prod(rs.getString("categorie_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setQuantite_stockee(rs.getInt("quantite_stockee"));

                produitsList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération : " + ex.getMessage());
        }

        return produitsList;
    }

    @Override
    public boolean update(produits p) {
        String qry = "UPDATE produits SET  prix_prod=?, categorie_prod=?, nom_prod=?, quantite_stockee=? WHERE id_prod=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, p.getId_prod());
            pstm.setString(2, p.getCategorie_prod());
            pstm.setFloat(3, p.getPrix_prod());
            pstm.setInt(4, p.getQuantite_stockee());
            pstm.setInt(5, p.getId_prod());

            return pstm.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(produits p) {
        String qry = "DELETE FROM produits WHERE id_prod=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, p.getId_prod());
            pstm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression : " + ex.getMessage());
            return false;
        }
    }

    @Override
    public produits getById(int id) {
        String qry = "SELECT * FROM produits WHERE id_prod=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                produits p = new produits();
                p.setId_prod(rs.getInt("id_prod"));
                p.setNom_prod(rs.getString("nom_prod"));
                p.setCategorie_prod(rs.getString("categorie_prod"));
                p.setPrix_prod(rs.getFloat("prix_prod"));
                p.setQuantite_stockee(rs.getInt("quantite_stockee"));
                return p;
            }

        } catch (SQLException ex) {
            System.out.println("Erreur dans getById : " + ex.getMessage());
        }

        return null;
    }

    public boolean verifierQuantiteDisponible(int id_prod, int quantiteDemandee) {
        String qry = "SELECT quantite_stockee FROM produits WHERE id_prod=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id_prod);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int quantiteActuelle = rs.getInt("quantite_stockee");
                return quantiteActuelle >= quantiteDemandee;
            }

        } catch (SQLException ex) {
            System.out.println("Erreur dans verifierQuantiteDisponible : " + ex.getMessage());
        }

        return false;
    }

    public boolean retirerQuantite(int id_prod, int quantiteRetirer) {
        String selectQry = "SELECT quantite_stockee FROM produits WHERE id_prod=?";
        String updateQry = "UPDATE produits SET quantite_stockee=? WHERE id_prod=?";

        try (PreparedStatement selectStmt = cnx.prepareStatement(selectQry);
             PreparedStatement updateStmt = cnx.prepareStatement(updateQry)) {

            selectStmt.setInt(1, id_prod);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int quantiteActuelle = rs.getInt("quantite_stockee");
                int nouvelleQuantite = quantiteActuelle - quantiteRetirer;

                if (nouvelleQuantite < 0) {
                    System.out.println("Quantité insuffisante !");
                    return false;
                }

                updateStmt.setInt(1, nouvelleQuantite);
                updateStmt.setInt(2, id_prod);
                updateStmt.executeUpdate();

                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Erreur dans retirerQuantite : " + ex.getMessage());
        }

        return false;
    }

}
