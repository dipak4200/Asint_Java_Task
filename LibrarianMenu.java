import java.util.Scanner;

public class LibrarianMenu {
    private static final Scanner sc = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1: addBook(); break;
                case 2: library.viewAllBooks(); break;
                case 3: searchBook(); break;
                case 4: borrowBook(); break;
                case 5: returnBook(); break;
                case 0: System.exit(0);
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void addBook() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        System.out.print("Genre: ");
        String genre = sc.nextLine();
        System.out.print("Available Copies: ");
        int copies = sc.nextInt();

        Book book = new Book(id, title, author, genre, copies);
        library.addBook(book);
    }

    private static void searchBook() {
        System.out.print("Enter keyword to search: ");
        String keyword = sc.nextLine();
        library.searchBooks(keyword);
    }

    private static void borrowBook() {
        System.out.print("Enter Book ID to borrow: ");
        int id = sc.nextInt();
        library.borrowBook(id);
    }

    private static void returnBook() {
        System.out.print("Enter Book ID to return: ");
        int id = sc.nextInt();
        library.returnBook(id);
    }
}
