package fr.univamu.iut.book;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.sql.SQLException;

@Path("/hello-world")
public class HelloResource {

    Dotenv dotenv = Dotenv.load();

    String db_url = dotenv.get("DB_URL");
    String db_user = dotenv.get("DB_USER");
    String db_password = dotenv.get("DB_PASSWORD");

    BookRepositoryMariadb db = new BookRepositoryMariadb(db_url, db_user , db_password);

    public HelloResource() throws SQLException, ClassNotFoundException {
    }


    @GET
    @Produces("text/plain")
    public String hello() {
        return db.getAllBooks().toString();
    }
}