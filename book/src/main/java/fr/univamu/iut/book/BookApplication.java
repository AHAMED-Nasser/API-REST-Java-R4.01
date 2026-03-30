package fr.univamu.iut.book;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class BookApplication extends Application {

    Dotenv dotenv = Dotenv.load();

    String db_url = dotenv.get("DB_URL");
    String db_user = dotenv.get("DB_USER");
    String db_password = dotenv.get("DB_PASSWORD");

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        // Création de la connection à la base de données et initialisation du service associé
        BookService service = null ;
        try{
            BookRepositoryMariadb db = new BookRepositoryMariadb(db_url, db_user, db_password);
            service = new BookService(db);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }

        // Création de la ressource en lui passant paramètre les services à exécuter en fonction
        // des différents endpoints proposés (i.e. requêtes HTTP acceptées)
        set.add(new BookRessource(service));

        return set;
    }

    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface BookRepositoryInterface utilisée
     *          pour accéder aux données des livres, voire les modifier
     */
    @Produces
    private BookRepositoryInterface openDbConnection() {
        BookRepositoryMariadb db = null;
        try {
            db = new BookRepositoryMariadb(db_url, db_user , db_password);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param bookRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes BookRepositoryInterface bookRepo) {
        bookRepo.close();
    }
}
