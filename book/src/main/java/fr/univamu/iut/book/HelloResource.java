package fr.univamu.iut.book;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.sql.SQLException;

@Path("/hello-world")
public class HelloResource {

    BookRepositoryMariadb db = new BookRepositoryMariadb("jdbc:mariadb://mysql-blog-td.alwaysdata.net/blog-td_library", "blog-td", "Rb.velocity+6");

    public HelloResource() throws SQLException, ClassNotFoundException {
    }


    @GET
    @Produces("text/plain")
    public String hello() {
        return db.getAllBooks().toString();
    }
}