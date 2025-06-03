// Interface with method declaration
interface ContactDetail {
    void showContactDetails();
}

// Abstract class with constructor and method
abstract class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }

    abstract void customerPay();

    void customerDetail() {
        System.out.println("Customer name: " + name);
    }
}

// Concrete class
class Dipak extends Customer implements ContactDetail {
    int number = 123;
    String email = "abc@gmail.com";

    Dipak(String name) {
        super(name);
    }

    @Override
    void customerPay() {
        System.out.println("Detail of Customer: name: " + name + ", number: " + number + ", email: " + email);
    }

    @Override
    public void showContactDetails() {
        System.out.println("Contact Number: " + number + ", Email: " + email);
    }
}

// Main class
public class personDetail {
    public static void main(String[] args) {
        Dipak test = new Dipak("abcd");
        test.customerPay();
        test.customerDetail();
        test.showContactDetails();
    }
}
