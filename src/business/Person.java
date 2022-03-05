package business;

import java.io.Serializable;

public class Person implements Serializable {
    private final static long serialVersionUID = 5454334345454333233L;
    private String firstName;
    private String lastName;
    private String phone;
    private Address address;

    public Person() {
    }

    public Person(String firstName, String lastName, String phone, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String getLastName() {
        return lastName;
    }


    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return
                "\n   firstName='" + firstName + '\'' +
                        "\n   lastName='" + lastName + '\'' +
                        "\n   phone='" + phone + '\'' +
                        "\n     address=" + address.toString();

    }

    public String getFirstName() {
        return firstName;
    }


}
