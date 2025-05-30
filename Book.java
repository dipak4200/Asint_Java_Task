public class Book {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int availableCopies;

    public Book(int id, String title, String author, String genre, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availableCopies = availableCopies;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Available Copies: " + availableCopies;
    }
}
