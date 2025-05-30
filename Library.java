import java.util.*;

public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully!");
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
        for (Book book : books) {
            if (book.getId() == id) {
                if (book.getAvailableCopies() > 0) {
                    book.setAvailableCopies(book.getAvailableCopies() - 1);
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("No available copies to borrow.");
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

