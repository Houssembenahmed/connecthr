package tn.esprit.interfaces;
import tn.esprit.models.produits;
import java.util.List;
public interface Icrud<T> {


    boolean add(T t);
    List<T> getAll();
    boolean update (T t);
    boolean delete (T t);

    produits getById(int id);
    boolean verifierQuantiteDisponible(int id_prod, int quantiteDemandee);
    boolean retirerQuantite(int id_prod, int quantiteRetirer);


    }
