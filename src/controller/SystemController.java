package controller;

import business.*;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.time.LocalDate;
import java.util.List;

public class SystemController {
    private static final DataAccessFacade dataAccess = new DataAccessFacade();

    public static Auth logIn(String username, String password) {
        DataAccessFacade accessFacade = new DataAccessFacade();
        //      accessFacade.readBooksMap();
        //        .get("23-11451");
        User user = accessFacade.readUserMap().get(username);
        if (user != null && user.getId().equals(username) && user.getPassword().equals(password)) {
            return user.getAuthorization();
        } else {
            System.out.println("Invalid username or password");
            return null;
        }
    }

    public static int addLibraryMember(String memberId, String firstName, String lastName, String street,
                                       String city, String state, String zip, String phone) {
        LibraryMember libraryMember = new LibraryMember(memberId, firstName, lastName, phone, new Address(street, zip, city, state));
        dataAccess.saveNewMember(libraryMember);
        return dataAccess.readMemberMap().size();
    }

    public static void checkoutBook(String memberId, String isBn) {
        try {
            boolean found = false;
            BookCopy copyBook = null;
            Book book = searchBookByIsBn(isBn);

            if (book != null) {
                copyBook = book.checkAvailable(isBn);
                if (copyBook == null) {
                    System.out.println("Sorry : There is no Book Copy Available ");
                } else {
                    LibraryMember member = dataAccess.readMemberMap().get(memberId);
                    CheckOutRecord checkOutRecord = member.getCheckOutRecord();
                   if(checkOutRecord==null) {
                       checkOutRecord=new CheckOutRecord();
                       checkOutRecord.setMemberId(memberId);
                   }



                    copyBook.setAvailable(false);
                    CheckOutRecordEntry checkOutRecordEntry = new CheckOutRecordEntry(isBn, LocalDate.now(),
                            null, copyBook, LocalDate.now().plusDays(book.getMaxCheckoutLength()));

                    checkOutRecord.addCheckOutRecordEntry(checkOutRecordEntry);
                    book.getBookCopies().get(book.getBookCopies().indexOf(copyBook)).setAvailable(false);



                    member.setCheckOutRecord(checkOutRecord);
                    dataAccess.saveNewMember(member);


                    System.out.println("Congratulations check out operation  done successfully " + "\n"
                            + checkOutRecordEntry);
                    System.out.println("all checout records for this Member " + memberId + "\n are :- " + checkOutRecord);
                }

            } else {
                System.out.println("Sorry : The Book is not Found ");
            }

        } catch (Exception e) {
            System.out.println("Sorry Something Wrong happened " + e.getMessage());
        }

    }

    public static void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        Book book = new Book(isbn, title, maxCheckoutLength, authors);
        dataAccess.saveNewBook(book);
    }


    public static Book searchBookByIsBn(String isBn) {
        Book book = new Book();
        try {
            DataAccess dataAccess = new DataAccessFacade();

            book = dataAccess.readBooksMap().get(isBn);

        } catch (
                Exception e) {
            System.out.println("Sorry Something Wrong happened " + e.getMessage());
        }
        return book;
    }

    public static LibraryMember searchForMember(String memberId) {
        LibraryMember member = new LibraryMember();
        try {
            DataAccess dataAccess = new DataAccessFacade();

            member = dataAccess.readMemberMap().get(memberId);

        } catch (
                Exception e) {
            System.out.println("Sorry Something Wrong happened " + e.getMessage());
        }
        return member;
    }
    public static void printCheckOutRecord(String memberId) {
        try {
            boolean found = false;

            LibraryMember member = searchForMember(memberId);

            if (member != null) {
                System.out.println("the Member check out history is  \n"+member.getCheckOutRecord().toString());
                }

             else {
                System.out.println("Sorry : The Member Id  is not Found ");
            }

        } catch (Exception e) {
            System.out.println("Sorry Something Wrong happened " + e.getMessage());
        }

    }

    public static void addBookCopy(String memberId, String isBn) {
        try {
            Book book = searchBookByIsBn(isBn);
            if (book != null) {
                book.addCopy();
                dataAccess.saveNewBook(book);
                System.out.println("Congratulations add copy operation  done successfully " + "\n now the book details are :- " + book);


            } else {
                System.out.println("Sorry : The Book is not Found ");
            }
        } catch (Exception e) {
            System.out.println("Sorry Something Wrong happened " + e.getMessage());
        }
    }
}