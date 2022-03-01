import java.util.Scanner;

public class MainApp {
    public static  void main(String [] args){

// write your code here
        Scanner in = new Scanner(System.in);
        String username;
        String password;
        System.out.println("========== (`_´)=======");
        System.out.println("Welcome to MPP Library!");
        System.out.println("User Login ");
        System.out.print("Username: ");
        username = in.next();
        System.out.print("Password: ");
        password = in.next();
        //TODO check login

        while (in.hasNext()) {

            //TODO login user
            System.out.println("Please choose an option below. Enter quit to exit.");
            System.out.println("1. Checkout book");
            System.out.println("2. Add library member");
            System.out.println("3. Add book copy");
            String input = in.next();
            if (input == "quit") {
                break;
            }
            switch (input){
                case "1" :
                    System.out.println("Showing checkout window");
                 case "2" :
                    System.out.println("Showing member registration");
                 case "3" :
                    System.out.println("Showing add book copy window");
                default :
                    System.out.println("Invalid choice");

            }
            System.out.println(" " + in.next());
        }

    }
}
