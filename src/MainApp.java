import business.Author;
import business.Book;
import business.LibraryMember;
import controller.AuthController;
import controller.SystemController;
import dataaccess.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static  void main(String [] args){

// write your code here
        Auth userRole;
        AuthController auth = new AuthController();
        Scanner in = new Scanner(System.in);
        String username, password;
        System.out.println("========== (`_´)=======");
        System.out.println("Welcome to MPP Library!");
        System.out.println("User Login ");
        System.out.print("UserId: ");
        username = in.next();
        System.out.print("Password: ");
        password = in.next();
        //TODO check login
        while (true){
            userRole = auth.logIn(username, password);
            if ( userRole!= null){
                break;
            };
            System.out.println("Please try again.");
            System.out.print("UserId: ");
            username = in.next();
            System.out.print("Password: ");
            password = in.next();
        }
        System.out.println("----------------------------------------------------");
        System.out.println("                    Main Menu");
        System.out.println("----------------------------------------------------");
        System.out.println("Please choose an option below. Enter 9 to exit.");
        System.out.println("\t1. Checkout book");
        System.out.println("\t2. Add book copy");
        System.out.println("\t3. Add book");
        if (userRole != null && (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH") )) {
            System.out.println("\t4. Add library member");
        }
        System.out.println("\t9. Quit");
        int input = 0;
        while (in.hasNext()) {
            try {
                input = Integer.parseInt(in.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter one of the choices above.");
            }
            if (input == 9) {
                System.out.println("Thanks you for vising our library. Bye!");
                break;
            }
            switch (input){
                case 1 :
                    System.out.println("Showing checkout window");
                    break;
                case 2 :
                    System.out.println("Showing add book copy window");
                    break;
                case 3: // add book
                    System.out.println("Enter new book details");
                    String isbn, title;
                    int maxCheckoutLength;
                    List<Author> authorList = new ArrayList<>();
                    Author author = new Author();
                    System.out.print("ISBN: ");
                    isbn = in.next();
                    System.out.print("Title: ");
                    title = in.next();
                    System.out.print("MaxCheckoutLength (days): ");
                    maxCheckoutLength = Integer.parseInt(in.next());
                   // System.out.println("Authors: ");

                    SystemController.addBook(isbn,title,maxCheckoutLength,new ArrayList<>());
                    break;
                case 4 :
                        System.out.println("======Adding new library member =====");
                        System.out.println("Please fill in the following details. ");
                        String memberId, firstName, lastName, city, state, street, zip, telephone;
                        System.out.print("MemberId: ");
                        memberId = in.next();
                        System.out.print("FirstName: ");
                        firstName = in.next();
                        System.out.println("LastName: ");
                        lastName = in.next();
                        System.out.print("Street: ");
                        street = in.next();
                        System.out.print("City: ");
                        city = in.next();
                        System.out.print("State: ");
                        state = in.next();
                        System.out.print("zip: ");
                        zip = in.next();
                        System.out.print("telephone: ");
                        telephone = in.next();
                        int totalMembers = SystemController.addLibraryMember(memberId, firstName, lastName, street, city, state, zip, telephone);
                        System.out.printf("Registration successful. You are one of %s user", totalMembers);
                    break;

                default :
                    System.out.println("Invalid choice. Try again or enter 9 to exit.");
            }
            System.out.println("----------------------------------------------------");
            System.out.println("                    Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.println("Please choose an option below. Enter 9 to exit.");
            System.out.println("\t1. Checkout book");
            System.out.println("\t2. Add book copy");
            System.out.println("\t3. Add book");
            if (userRole != null && (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH") )) {
                System.out.println("\t4. Add library member");
            }
            System.out.println("\t9. Quit");
        }

    }
}
