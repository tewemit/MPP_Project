package business;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person{

    private String about;
    List<Book> books;

    public Author( String firstName, String lastName, String phone, Address address, String credentials) {
        super(firstName, lastName, phone, address);
        this.about = credentials;
        books=new ArrayList<Book>();

    }
    public void  addBook(Book book){
       books.add(book) ;
    }

    public String about() {
        return about;
    }

    public List<Book> getBooks() {
        return books;
    }
}
