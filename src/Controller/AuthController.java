package Controller;

import dataaccess.DataAccessFacade;
import dataaccess.User;

public class AuthController {
    public User getUserByUsername(String username, String password){
        DataAccessFacade accessFacade = new DataAccessFacade();
        User user =  accessFacade.readUserMap().get(username);
        if (user !=null && user.getId() == username && user.getPassword() == password) {
            return user;
        }
        else {
            System.out.println("Invalid username or password");
            return null;
        }
    }
}
