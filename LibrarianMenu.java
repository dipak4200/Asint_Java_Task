import java.util.Scanner;

public class LibrarianMenu {
    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n=== Library Menu ===");
                System.out.println("1. Add Book");
                System.out.println("2. View All Books");
                System.out.println("3. Search Book");
                System.out.println("4. Borrow Book");
                System.out.println("5. Return Book");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1: addBook(); break;
                    case 2: library.viewAllBooks(); break;
                    case 3: searchBook(); break;
                    case 4: borrowBook(); break;
                    case 5: returnBook(); break;
                    case 0: System.exit(0);
                    default: System.out.println("Invalid option. Please enter a number between 0 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values only.");
            }
        }
    }

    private static void addBook() {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Title: ");
            String title = sc.nextLine();

            System.out.print("Author: ");
            String author = sc.nextLine();

            System.out.print("Genre: ");
            String genre = sc.nextLine();

            System.out.print("Available Copies: ");
            int copies = Integer.parseInt(sc.nextLine());

            Book book = new Book(id, title, author, genre, copies);
            library.addBook(book);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values for ID and copies.");
        }
    }

    private static void searchBook() {
        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine().trim();
        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
        } else {
            library.searchBooks(keyword);
        }
    }

    private static void borrowBook() {
        try {
            System.out.print("Enter Book ID to borrow: ");
            int id = Integer.parseInt(sc.nextLine());
            library.borrowBook(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid book ID.");
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter Book ID to return: ");
            int id = Integer.parseInt(sc.nextLine());
            library.returnBook(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid book ID.");
        }
    }
}
