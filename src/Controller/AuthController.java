package controller;

import business.BookCopy;
import business.CheckOutRecord;
import business.CheckOutRecordEntry;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import business.Book;

import java.time.LocalDate;
import java.util.HashMap;

public class AuthController {
    public User getUserByUsername(String username, String password) {
        DataAccessFacade accessFacade = new DataAccessFacade();
        User user = accessFacade.readUserMap().get(username);
        if (user != null && user.getId() == username && user.getPassword() == password) {
            return user;
        } else {
            System.out.println("Invalid username or password");
            return null;
        }
    }

    public void checkoutBook(String memberId, String isBn) {
        boolean found = false;
        BookCopy copyNumber = null;
        HashMap<String, Book> books = new HashMap<>();
        DataAccess dataAccess = new DataAccessFacade();
        books = dataAccess.readBooksMap();
        Book b = new Book();
        String[] booksIsBns = books.keySet().toArray(new String[0]);
        for (String isBnItem : booksIsBns) {
            if (isBnItem == isBn) {
                found = true;
                b = books.get(isBnItem);
                copyNumber = b.checkAvailable(isBn);
                break;

            }
        }
        if (found == false) {
            System.out.println("Sorry : The Book is not Found ");
        } else if (copyNumber == null) {
            System.out.println("Sorry : The Book is not Found ");
        } else {
            CheckOutRecord checkOutRecord = new CheckOutRecord();
            checkOutRecord.setIsBn(isBn);
            checkOutRecord.setMemberId(memberId);

            CheckOutRecordEntry checkOutRecordEntry = new CheckOutRecordEntry(isBn, LocalDate.now(),
                    null, copyNumber, LocalDate.now().plusDays(b.getMaxCheckoutLength()));

            checkOutRecord.addCheckOutRecordEntry(checkOutRecordEntry);

        }
    }
}
