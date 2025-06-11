import java.util.*;

public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book newBook) {
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book existing = it.next();
            if (existing.getTitle().equalsIgnoreCase(newBook.getTitle()) &&
                    existing.getAuthor().equalsIgnoreCase(newBook.getAuthor())) {

                int updatedCopies = existing.getAvailableCopies() + newBook.getAvailableCopies();
                existing.setAvailableCopies(updatedCopies);
                System.out.println("Book already exists. Copies updated to " + updatedCopies);
                return;
            }
        }

        books.add(newBook);
        System.out.println("New book added.");
    }


    public void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public void searchBooks(String keyword) {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    public void borrowBook(int id) {
        Iterator<Book> it = books.iterator();
        while (it.hasNext()) {
            Book book = it.next();
            if (book.getId() == id) {
                if (book.getAvailableCopies() > 0) {
                    book.setAvailableCopies(book.getAvailableCopies() - 1);
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("No available copies.");
                }
                return;
            }
        }
        System.out.println("Book ID not found.");
    }


    public void returnBook(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book ID not found.");
    }
}
