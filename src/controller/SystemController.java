package controller;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.util.List;

public class SystemController {
    private static  final DataAccessFacade dataAccess = new DataAccessFacade();

    public static int addLibraryMember(String  memberId, String firstName, String lastName, String street,
                                                 String city, String state, String zip, String phone) {
        LibraryMember libraryMember = new LibraryMember(memberId, firstName, lastName, phone, new Address(street, zip, city, state));
        dataAccess.saveNewMember(libraryMember);
        return dataAccess.readMemberMap().size();
    }

    public static void addBook(String  isbn, String title, int maxCheckoutLength, List<Author> authors) {
        Book book = new Book(isbn,title, maxCheckoutLength, authors);
        dataAccess.saveNewBook(book);
    }
}