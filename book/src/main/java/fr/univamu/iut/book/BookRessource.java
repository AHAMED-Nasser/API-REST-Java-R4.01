package fr.univamu.iut.book;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/books")
@ApplicationScoped
public class BookRessource {
    /**
     * Service utilisé pour accéder aux données des livres et récupérer/modifier leurs informations
     */
    @Inject
    private BookService service;

    /**
     * Constructeur par défaut
     */
    public BookRessource() {}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param bookRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject BookRessource(BookRepositoryInterface bookRepo) {
        this.service = new BookService(bookRepo);
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     */
    public BookRessource(BookService service) {
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des livres (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllBooks() {
        return service.getAllBooksJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un livre dont la référence est passée paramètre dans le chemin
     * @param reference référence du livre recherché
     * @return les informations du livre recherché au format JSON
     */
    @GET
    @Path("{reference}")
    @Produces("application/json")
    public String getBook(@PathParam("reference") String reference) {
        String result = service.getBookJSON(reference);

        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    @PUT
    @Path("{reference}")
    @Produces("application/json")
    public Response updateBook(@PathParam("reference") String reference, Book book) {
        if (!service.updateBook(reference, book)) {
            throw new NotFoundException();
        } else {
            return Response.ok("updated").build();
        }
    }
}
