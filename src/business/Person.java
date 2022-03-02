package business;

public class Person {
    private String firstName;
    private String lastName;
    private String phone;
    private Address  address;

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



    public String getFirstName() {
        return firstName;
    }


}
