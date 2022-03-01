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
        while (in.hasNext()) {

            //TODO login user
            System.out.println("Please choose an option:");
            System.out.println("1. Checkout book");
            System.out.println("2. Checkin book");
            System.out.println(" " + in.next());
        }

    }
}
