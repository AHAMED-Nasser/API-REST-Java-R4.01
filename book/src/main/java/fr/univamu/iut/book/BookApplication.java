package fr.univamu.iut.book;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class BookApplication extends Application {

    Dotenv dotenv = Dotenv.load();

    String db_url = dotenv.get("DB_URL");
    String db_user = dotenv.get("DB_USER");
    String db_password = dotenv.get("DB_PASSWORD");

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
