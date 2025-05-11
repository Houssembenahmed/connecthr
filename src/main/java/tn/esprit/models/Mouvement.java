package tn.esprit.models;
import java.sql.Date;

public class Mouvement {
    public enum TypeMouv {
        ENTREE, SORTIE
    }
    private int id_mouv;
    private int id_produit;
    private TypeMouv type_mouv;
    private int quantite_mouv;
    private Date date_mouv;

    public Mouvement() {
    }

    public Mouvement(int id_prod, TypeMouv type_mouv, int quantite_mouv, Date date_mouv) {
        this.id_produit = id_prod;
        this.type_mouv = type_mouv;
        this.quantite_mouv = quantite_mouv;
        this.date_mouv = date_mouv;
    }

    public int getId_mouv() {
        return id_mouv;
    }

    public void setId_mouv(int id_mouv) {
        this.id_mouv = id_mouv;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }
    public TypeMouv getType_mouv() {
        return type_mouv;
    }

    public void setType_mouv(TypeMouv type_mouv) {
        this.type_mouv = type_mouv;
    }

    public int getQuantite_mouv() {
        return quantite_mouv;
    }

    public void setQuantite_mouv(int quantite_mouv) {
        if (quantite_mouv > 0) this.quantite_mouv = quantite_mouv;
    }

    public Date getDate_mouv() {
        return date_mouv;
    }

    public void setDate_mouv(Date date_mouv) {
        this.date_mouv = date_mouv;
    }

    @Override
    public String toString() {
        return "Mouvements{" +
                "id_mouv=" + id_mouv +
                ", type_mouv=" + type_mouv +
                ", quantite_mouv=" + quantite_mouv +
                ", date_mouv=" + date_mouv +
                '}';
    }
}
