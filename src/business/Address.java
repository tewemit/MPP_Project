package business;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serilialVersionUID = 5147265048973262145L;
    private final String street;
    private final String zip;
    private final String city;

    private final String state;

    public Address(String street, String zip, String city, String state) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return
                 "\n   street='" + street + '\'' +
                        "\n   zip='" + zip + '\'' +
                        "\n   city='" + city + '\'' +
                        "\n   state='" + state + '\'' ;

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
