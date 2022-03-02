package auth;

import java.util.List;

public class Auth {
    private String userName;
    private String password;
    private String email;
    private List<Role> roles;

    public Auth(String username, String password, String email, List<Role> roles) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Register [userName=" + userName + ", password=" +
                password + ", emailId=" + email+"]";
    }
}
