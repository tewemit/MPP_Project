import java.util.Scanner;

public class MainApp {
    public static  void main(String [] args){

// write your code here
        Scanner in = new Scanner(System.in);
        String username, password;
        System.out.println("========== (`_´)=======");
        System.out.println("Welcome to MPP Library!");
        System.out.println("User Login ");
        System.out.print("Username: ");
        username = in.next();
        System.out.print("Password: ");
        password = in.next();
        //TODO check login

        System.out.println("Please choose an option below. Enter 9 to exit.");
        System.out.println("1. Add library member");
        System.out.println("2. Checkout book");
        System.out.println("3. Add book copy");
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
                    System.out.println("Showing member registration");
                    break;
                case 2 :
                    System.out.println("Showing checkout window");
                    break;
                 case 3 :
                    System.out.println("Showing add book copy window");
                    break;
                default :
                    System.out.println("Invalid choice");
                    break;

            }
            System.out.println("Ready for next choice ");
        }

    }
}
