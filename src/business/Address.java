package business;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serilialVersionUID = 5147265048973262145L;
    private String street;
    private String zip;
    private String city;

    private String state;

    public Address(String street, String zip, String city, String state) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
