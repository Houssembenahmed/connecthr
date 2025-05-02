package tn.esprit.services;
import tn.esprit.models.mouvements;
import tn.esprit.utils.connecthrDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class mouvementservice {

    private Connection cnx;

    public mouvementservice() {
        cnx = connecthrDB.getInstance().getCnx();
    }

    public boolean add(mouvements m) {
        String qry = "INSERT INTO mouvements (id_prod, type_mouv, quantite_mouv, date_mouv) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, m.getId_produit()); // Ajout de id_produit
            pstm.setString(2, m.getType_mouv().name());
            pstm.setInt(3, m.getQuantite_mouv());
            pstm.setDate(4, new java.sql.Date(m.getDate_mouv().getTime()));

            pstm.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout du mouvement : " + ex.getMessage());
            return false;
        }
    }

    public List<mouvements> getAll() {
        List<mouvements> mouvements = new ArrayList<>();
        String qry = "SELECT * FROM mouvements";

        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {

            while (rs.next()) {
                mouvements m = new mouvements();
                m.setId_mouv(rs.getInt("id_mouv"));
                m.setType_mouv(tn.esprit.models.mouvements.TypeMouv.valueOf(rs.getString("type_mouv")));
                m.setQuantite_mouv(rs.getInt("quantite_mouv"));
                m.setDate_mouv(rs.getDate("date_mouv"));

                mouvements.add(m);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des mouvements : " + ex.getMessage());
        }

        return mouvements;
    }

    public boolean delete(int id_mouv) {
        String qry = "DELETE FROM mouvements WHERE id_mouv=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id_mouv);
            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression du mouvement : " + ex.getMessage());
            return false;
        }
    }

    public mouvements getById(int id_mouv) {
        String qry = "SELECT * FROM mouvements WHERE id_mouv=?";

        try (PreparedStatement pstm = cnx.prepareStatement(qry)) {
            pstm.setInt(1, id_mouv);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                mouvements m = new mouvements();
                m.setId_mouv(rs.getInt("id_mouv"));
                m.setType_mouv(mouvements.TypeMouv.valueOf(rs.getString("type_mouv")));
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

