import business.Author;

import controller.SystemController;
import dataaccess.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {

// write your code here

        Auth userRole;

        Scanner in = new Scanner(System.in);
        String username, password;
        System.out.println(ANSI_BLUE + "========== "+ ANSI_YELLOW + " (`_´) " + ANSI_BLUE + " =======" + ANSI_RESET);
        System.out.println("Welcome to MPP Library!");
        System.out.println(ANSI_BLUE + "=========================" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "User Login " + ANSI_RESET);
        System.out.print("UserId: ");
        username = in.next();
        System.out.print("Password: ");
        password = in.next();
        //TODO check login
        while (true) {
            userRole = SystemController.logIn(username, password);
            if (userRole != null) {
                break;
            }
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

        if (userRole != null && (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH") )) {
            System.out.println("\t3. Add book");
            System.out.println("\t4. Add library member");
            System.out.println("\t5. Search book by ISBN");
            System.out.println("\t6. Search library member");
            System.out.println("\t7. Print Check Out Record For Member Id");
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
                    System.out.println("Please Enter  book details");
                    String isBn;
                    System.out.print("ISBN: ");
                    isBn = in.next();
                    System.out.println("Enter member Id:");
                    String memberId ;
                    memberId= in.next();
                    SystemController.checkoutBook(memberId, isBn);
                    break;
                case 2 :
                    if (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH")) {
                        System.out.println("Please Enter  book details");
                        isBn = "";
                        System.out.print("ISBN: ");
                        isBn = in.next();

                        SystemController.addBookCopy( isBn);
                    }
                    else
                        System.out.println("You are not authorized to add book copy.");
                    break;
                case 3 : // add book
                    if (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH")) {
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
                        try {
                            maxCheckoutLength = Integer.parseInt(in.next());
                        } catch (NumberFormatException e) {
                            System.out.println(ANSI_RED +"Checkout length must be numeric. Type again:" + ANSI_RESET);
                            System.out.print("MaxCheckoutLength (days): ");
                            maxCheckoutLength = Integer.parseInt(in.next());
                        }
                        // System.out.println("Authors: ");
                        SystemController.addBook(isbn,title,maxCheckoutLength,new ArrayList<>());
                    }
                    else
                        System.out.println("You are not authorized to add book.");

                    break;
                case 4 :
                    if (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH")) {
                        System.out.println("======Adding new library member =====");
                        System.out.println("Please fill in the following details. ");
                        String  firstName, lastName, city, state, street, zip, telephone;
                        System.out.print("MemberId: ");
                        memberId = in.next();
                        System.out.print("FirstName: ");
                        firstName = in.next();
                        System.out.print("LastName: ");
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
                        System.out.println("----------------------------------------------------");
                        System.out.printf(ANSI_GREEN + "Registration successful. You are one of %s user \n", totalMembers + "(s)" +ANSI_RESET);
                    }
                    else
                        System.out.println("You are not authorized to add member.");
                    break;
                case 5:
                    if (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH")) {
                        System.out.println("Enter ISBN to search:");
                        SystemController.searchAndShowBook(in.next());

                    }
                    else
                    System.out.println("You are not authorized to search books with ISBN.");
                    break;
                case 6:
                    if (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH")) {
                        System.out.println("Enter member Id:");
                         memberId = in.next();
                        if(SystemController.searchMemberById(memberId)){
                            // show checkout records
                            SystemController.showMemberCheckoutRecords(memberId);
                        }

                    }
                    else
                    System.out.println("You are not authorized to search books with ISBN.");
                    break;
                case 7:
                    System.out.println("Enter member Id:");
                    memberId= in.next();
                    SystemController.printCheckOutRecord(memberId);
                default :
                    System.out.println("Invalid choice. Try again or enter 9 to exit.");
            }
            System.out.println("----------------------------------------------------");
            System.out.println("                    Main Menu");
            System.out.println("----------------------------------------------------");
            System.out.println("Please choose an option below. Enter 9 to exit.");
            System.out.println("\t1. Checkout book");
            System.out.println("\t2. Add book copy");
            if (userRole != null && (userRole.name().equals("ADMIN") || userRole.name().equals("BOTH") )) {
                System.out.println("\t3. Add book");
                System.out.println("\t4. Add library member");
                System.out.println("\t5. Search book by ISBN");
                System.out.println("\t6. Search library member");
                System.out.println("\t7. Print Check Out Record For Member Id");
            }
            System.out.println("\t9. Quit");
        }

    }
}
