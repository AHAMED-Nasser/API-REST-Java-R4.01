package fr.univamu.iut.book;

public class Book {
    protected String reference;
    protected String title;
    protected String authors;
    protected char status;

    public Book() {}

    public Book(String reference, String title, String authors) {
        this.reference = reference;
        this.title = title;
        this.authors = authors;
        this.status = 'd';
    }

    public String getReference() { return this.reference; }
    public String getTitle() { return this.title; }
    public String getAuthors() { return this.authors; }
    public char getStatus() { return this.status; }

    public void setReference(String newReference) { this.reference = newReference; }
    public void setTitle(String newTitle) { this.title = newTitle; }
    public void setAuthors(String newAuthors) { this.authors = newAuthors; }
    public void setStatus(char newStatus) { this.status = newStatus; }

    @Override
    public String toString() {
        return "Livre{" +
                "reference='" + reference + '\'' +
                ", titre='" + title + '\'' +
                ", auteurs='" + authors + '\'' +
                ", statut=" + status +
                '}';
    }
}
