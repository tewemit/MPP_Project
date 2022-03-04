package controller;

import business.*;
import dataaccess.DataAccessFacade;
import exceptions.SystemExceptions;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        System.out.println("Booked added successfully.");
        searchAndShowBook(book.getIsBn());

    }
    public static String searchAndShowBook(String  isbn) {
        Book book = dataAccess.readBooksMap().get(isbn);
        if (book == null) {
           return "No book found with ISBN: " + isbn;
        }
        System.out.println("================||Book details for " + isbn + " ||================" );
        System.out.println("ISBN \t\t CopyNo \t\t Title \t\t CheckoutDate \t\t DueDate \t\t CheckedOutBy");
        System.out.println("---- \t\t ------------ \t\t -------");
        System.out.println("\n**" + book.getIsBn() + "\t\t** Title: " + book.getTitle());a
        List<CheckOutRecord> checkOutRecords = dataAccess.readMemberMap().values()
                .stream().map(LibraryMember::getCheckOutRecord).collect(Collectors.toList());

        book.getBookCopies().forEach(bookCopy -> {

            CheckOutRecordEntry checkOutRecordEntries = (CheckOutRecordEntry) checkOutRecords.stream().map(CheckOutRecord::getCheckOutEntries)
                    .map(checkOutRecordEntries1 -> checkOutRecordEntries1.stream().filter(cf -> cf.getBookCopy() == bookCopy))
                    .map(checkOutRecordEntryStream -> checkOutRecordEntryStream.
                            anyMatch(checkOutRecordEntry -> checkOutRecordEntry.getBookCopy() == bookCopy ));

             System.out.println(bookCopy.getIsBn() + "\t\t" + bookCopy.getCopyNumber() + "\t\t" +book.getTitle()
             + "\t\t" +checkOutRecords.stream().map(CheckOutRecord::getCheckOutEntries)
                     .map(checkOutRecordEntries1 -> checkOutRecordEntries1.stream().filter(cf -> cf.getBookCopy() == bookCopy))
                     .map(checkOutRecordEntryStream -> checkOutRecordEntryStream.
                             anyMatch(checkOutRecordEntry -> checkOutRecordEntry.getBookCopy() == bookCopy )));

        });
        return
                "\n** Title: " + book.getTitle() +
                "\n** ISBN: " + book.getIsBn() ;
    }

    public static boolean searchMemberById(String memberId) {
        HashMap<String, LibraryMember> members = dataAccess.readMemberMap();
        LibraryMember member = dataAccess.readMemberMap().get(memberId);
        if (member == null) {
            System.out.println("Member not found. Please try again");
            return false;
        }
        else {
            System.out.println("================|| Member details ||================" +
                    "\n** MemberId:"+ member.getMemberId() +
                    "\n** FirstName:" + member.getFirstName() +
                    "\n** LastName:" + member.getLastName());
            System.out.println("=================================================");
            System.out.println("Choose option below for the member: ");
            System.out.println("8 Show checkout records");
            return  true;
        }
    }

    public static void showMemberCheckoutRecords(String memberId) {
        CheckOutRecord checkOutRecord = dataAccess.readMemberMap().get(memberId).getCheckOutRecord();
        if (checkOutRecord == null) {
            System.out.println("No checkout record found for member: " + memberId);
            return;
        }
        checkOutRecord.getCheckOutEntries().forEach(ckrEntry -> {
            System.out.println("ISBN \t\t CheckoutDate \t\t DueDate");
            System.out.println("---- \t\t ------------ \t\t -------");
            System.out.println("|"+ ckrEntry.getBookCopy().getIsBn() + "| \t\t" +
                    ckrEntry.getCheckOutDate() + "| \t\t" + ckrEntry.getDueDate());
        });
    }
}