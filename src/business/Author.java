package business;

import java.io.Serializable;
import java.util.List;

public class Author extends Person implements Serializable {
    private static final long serialVersionUID = 7508481940058530471L;
    private String about;

    public Author() {
    }

    public Author(String about, List<Book> books) {
        this.about = about;
    }

    public Author(String firstName, String lastName, String phone, Address address, String credentials) {
        super(firstName, lastName, phone, address);
        this.about = credentials;

    }

    @Override
    public String toString() {
        return
                "\n   firstName='" + getFirstName() +
                "\n   lastName='" + getLastName() +
                "\n   phone='" + getPhone() +
                "\n   about='" + about +
                "\n   address='" + getAddress() + '\'';
    }

    public String getAbout() {
        return about;
    }

}
