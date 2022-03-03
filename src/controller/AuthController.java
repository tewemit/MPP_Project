package controller;

import dataaccess.DataAccessFacade;
import dataaccess.User;
import dataaccess.Auth;


public class AuthController {
    public Auth logIn(String username, String password){
        DataAccessFacade accessFacade = new DataAccessFacade();
        accessFacade.readBooksMap();
        //        .get("23-11451");
        User user =  accessFacade.readUserMap().get(username);
        if (user != null && user.getId().equals(username) && user.getPassword().equals(password)) {
            return user.getAuthorization();
        }
        else {
            System.out.println("Invalid username or password");
            return null;
        }
    }
}
