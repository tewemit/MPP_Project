package business;

import dataaccess.Auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Author extends Person implements Serializable {
    private static final long serialVersionUID = 7508481940058530471L;
    private String about;

    public Author() {
    }

    public Author(String about, List<Book> books) {
        this.about = about;
    }

    @Override
    public String toString() {
        return
                "\n   about='" + about + '\'';
    }

    public Author(String firstName, String lastName, String phone, Address address, String credentials) {
        super(firstName, lastName, phone, address);
        this.about = credentials;

    }


    public String about() {
        return about;
    }

}
