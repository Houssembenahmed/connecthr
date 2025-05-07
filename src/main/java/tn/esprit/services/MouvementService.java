package tn.esprit.services;
import tn.esprit.interfaces.Icrud;
import tn.esprit.models.Mouvement;
import tn.esprit.utils.connecthrDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MouvementService implements Icrud<Mouvement> {

    private Connection cnx;

    public MouvementService() {
        cnx = connecthrDB.getInstance().getCnx();
    }

    public boolean add(Mouvement m) {
        ProduitService ps = new ProduitService();

        if (m.getType_mouv() == Mouvement.TypeMouv.SORTIE && ps.verifierQuantiteDisponible(m.getId_produit(), m.getQuantite_mouv())) {
            String qry = "INSERT INTO mouvements (id_prod, type_mouv, quantite_mouv, date_mouv) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
                pstm.setInt(1, m.getId_produit());
                pstm.setString(2, m.getType_mouv().name());
                pstm.setInt(3, m.getQuantite_mouv());
                pstm.setDate(4, new java.sql.Date(m.getDate_mouv().getTime()));

                ps.retirerQuantite(m.getId_produit(), m.getQuantite_mouv());

                pstm.executeUpdate();
                return true;

            } catch (SQLException ex) {
                System.out.println("Erreur lors de l'ajout du mouvement : " + ex.getMessage());
            }
        }
        if (m.getType_mouv() == Mouvement.TypeMouv.ENTREE) {
            String qry = "INSERT INTO mouvements (id_prod, type_mouv, quantite_mouv, date_mouv) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
                pstm.setInt(1, m.getId_produit());
                pstm.setString(2, m.getType_mouv().name());
                pstm.setInt(3, m.getQuantite_mouv());
                pstm.setDate(4, new java.sql.Date(m.getDate_mouv().getTime()));

                ps.ajouterQuantite(m.getId_produit(), m.getQuantite_mouv());

                pstm.executeUpdate();
                return true;
            } catch (SQLException ex) {
                System.out.println("Erreur lors de l'ajout du mouvement : " + ex.getMessage());
            }
        }
        return false;
    }
    public List<Mouvement> getAll() {
        List<Mouvement> Mvts = new ArrayList<>();
        String qry = "SELECT * FROM mouvements";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                Mouvement m = new Mouvement();
                m.setId_mouv(rs.getInt("id_mouv"));
                m.setType_mouv(Mouvement.TypeMouv.valueOf(rs.getString("type_mouv")));
                m.setQuantite_mouv(rs.getInt("quantite_mouv"));
                m.setDate_mouv(rs.getDate("date_mouv"));

                Mvts.add(m);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des mouvements : " + ex.getMessage());
        }
        return Mvts;
    }
    @Override
    public boolean update(Mouvement m) {

        ProduitService ps = new ProduitService();

        if (m.getType_mouv() == Mouvement.TypeMouv.SORTIE && ps.verifierQuantiteDisponible(m.getId_produit(), m.getQuantite_mouv())) {

            String qry = "UPDATE mouvements SET id_prod=?,type_mouv=?, quantite_mouv=?, date_mouv=? WHERE id_mouv=? ";

            try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
                pstm.setInt(1, m.getId_produit());
                pstm.setString(2, m.getType_mouv().name());
                pstm.setInt(3, m.getQuantite_mouv());
                pstm.setDate(4, m.getDate_mouv());

                ps.retirerQuantite(m.getId_produit(), m.getQuantite_mouv());

                return pstm.executeUpdate() > 0;

            } catch (SQLException ex) {
                System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
            }

            return false;
        }
        if (m.getType_mouv() == Mouvement.TypeMouv.ENTREE ) {

            String qry = "UPDATE mouvements SET id_prod=?,type_mouv=?, quantite_mouv=?, date_mouv=? WHERE id_mouv=? ";

            try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
                pstm.setInt(1, m.getId_produit());
                pstm.setString(2, m.getType_mouv().name());
                pstm.setInt(3, m.getQuantite_mouv());
                pstm.setDate(4, m.getDate_mouv());

                ps.ajouterQuantite(m.getId_produit(), m.getQuantite_mouv());

                return pstm.executeUpdate() > 0;

            } catch (SQLException ex) {
                System.out.println("Erreur lors de la mise à jour : " + ex.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean delete(Mouvement m) {
        String qry = "DELETE FROM mouvements WHERE id_mouv=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, m.getId_mouv());
            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du mouvement : " + ex.getMessage());
            return false;
        }    }


    public Mouvement getById(int id_mouv) {
        String qry = "SELECT * FROM mouvements WHERE id_mouv=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id_mouv);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                Mouvement m = new Mouvement();
                m.setId_mouv(rs.getInt("id_mouv"));
                m.setId_produit(rs.getInt("id_prod"));
                m.setType_mouv(Mouvement.TypeMouv.valueOf(rs.getString("type_mouv")));
                m.setQuantite_mouv(rs.getInt("quantite_mouv"));
                m.setDate_mouv(rs.getDate("date_mouv"));
                return m;
            }

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du mouvement : " + ex.getMessage());
        }

        return null;
    }
}

