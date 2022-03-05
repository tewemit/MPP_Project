package controller;

import business.*;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class SystemController {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
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
        System.out.println("----------------------------------------------------");
        System.out.printf(ANSI_GREEN + "Registration successful. You are one of %s user \n", dataAccess.readMemberMap().size() + "(s)" + ANSI_RESET);
        System.out.println(libraryMember);
        return dataAccess.readMemberMap().size();
    }

    public static void checkoutBook(String memberId, String isBn) {
        try {
            boolean found = false;
            BookCopy copyBook = null;
            Book book = searchBookByIsBn(isBn);
            LibraryMember member = searchForMember(memberId);

            if (member == null) {

                System.out.println(ANSI_RED + "Sorry : The Member Id  is not Found\n " + ANSI_RESET);
            }
            else {
                if (book != null) {
                    copyBook = book.checkAvailable(isBn);
                    if (copyBook == null) {
                        System.out.println("Sorry : There is no Book Copy Available ");
                    } else {
                //        LibraryMember member = dataAccess.readMemberMap().get(memberId);
                        CheckOutRecord checkOutRecord = member.getCheckOutRecord();
                        if (checkOutRecord == null) {
                            checkOutRecord = new CheckOutRecord();
                            checkOutRecord.setMemberId(memberId);
                        }


                        copyBook.setAvailable(false);
                        CheckOutRecordEntry checkOutRecordEntry = new CheckOutRecordEntry(isBn, LocalDate.now(),
                                null, copyBook, LocalDate.now().plusDays(book.getMaxCheckoutLength()));

                        checkOutRecord.addCheckOutRecordEntry(checkOutRecordEntry);
                        book.getBookCopies().get(book.getBookCopies().indexOf(copyBook)).setAvailable(false);


                    dataAccess.saveNewBook(book);
                    member.setCheckOutRecord(checkOutRecord);
                    dataAccess.saveCheckout(memberId, checkOutRecord);
                    dataAccess.saveNewMember(member);


                    System.out.printf(ANSI_GREEN +"Congratulations check out operation  done successfully " + "\n"
                            +ANSI_RESET);
                    System.out.println("ISBN: " + checkOutRecordEntry.getIsBn());
                    System.out.println("Checkout Date: " + checkOutRecordEntry.getCheckOutDate());
                    System.out.println("Copy Number: " + checkOutRecordEntry.getBookCopy().getCopyNumber());
                    System.out.println("Due Date: " + checkOutRecordEntry.getDueDate());
                   // System.out.println("\nAll checkout records for this Member " + memberId + "\n are :- " + checkOutRecord);
                }

                } else {
                    System.out.println(ANSI_RED + "Sorry : The Book is not Found \n " + ANSI_RESET);
                }
            }

        } catch (Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n " + ANSI_RESET);
        }

    }

    public static void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        Book book = new Book(isbn, title, maxCheckoutLength, authors);
        dataAccess.saveNewBook(book);
        System.out.printf(ANSI_GREEN +"Book added successfully.\n"+ANSI_RESET);
        searchAndShowBook(book.getIsBn());

    }

    public static void searchAndShowBook(String isbn) {
        Book book = dataAccess.readBooksMap().get(isbn);
        if (book == null) {
            System.out.println("No book found with ISBN: " + isbn);
            return;
        }
        System.out.println(ANSI_GREEN + "===========================||Book details for " + isbn + " ||======================================" + ANSI_RESET);
        System.out.println("ISBN \t|\t CopyNo \t|\t Title \t|\t CheckoutDate \t|\t DueDate \t|\t CheckedOutBy");
        System.out.println("---- \t|\t ------- \t|\t------ \t|\t------------ \t|\t ------- \t|\t ------------");
        List<BookCopy> copies = book.getBookCopies();
        List<CheckOutRecordEntry> entries = CheckOutRecord.getCheckoutEntriesByIsbn(book.getIsBn());

        for (BookCopy copy : copies) {
            entries.forEach(checkOutRecordEntry -> {
                String memberId = "";
                if (CheckOutRecord.getCheckedOutUserByIsbn(book.getIsBn(), copy.getCopyNumber()) != null) {
                    memberId = CheckOutRecord.getCheckedOutUserByIsbn(book.getIsBn(), copy.getCopyNumber()).get();
                }
                if (checkOutRecordEntry.getBookCopy().getCopyNumber() == copy.getCopyNumber()) {
                    System.out.println(copy.getIsBn() + "\t\t| \t" + copy.getCopyNumber() +
                            "\t\t\t| \t" + book.getTitle() +
                            "\t\t| \t" + checkOutRecordEntry.getCheckOutDate() + "\t\t| \t" + checkOutRecordEntry.getDueDate() + "\t\t| \t\t" + memberId);
                }
            });
        }

    }

    public static boolean searchMemberById(String memberId) {
        HashMap<String, LibraryMember> members = dataAccess.readMemberMap();
        LibraryMember member = dataAccess.readMemberMap().get(memberId);
        if (member == null) {
            System.out.println(ANSI_RED + "Member not found. Please try again\n " + ANSI_RESET);
            return false;
        } else {
            System.out.println("================|| Member details ||================" +
                    "\n** MemberId:" + member.getMemberId() +
                    "\n** FirstName:" + member.getFirstName() +
                    "\n** LastName:" + member.getLastName());
            System.out.println("=================================================");
            return  true;
        }
    }

    public static void showMemberCheckoutRecords(String memberId) {
        CheckOutRecord checkOutRecord = dataAccess.readMemberMap().get(memberId).getCheckOutRecord();
        if (checkOutRecord == null) {
            System.out.println("No checkout records found for member: " + memberId);
            System.out.println(ANSI_RED +"No checkout record found for member:" + memberId+"\n "+ ANSI_RESET);

            return;
        }
        System.out.println("ISBN \t\t CopyNumber \t\t CheckoutDate \t\t DueDate");
        System.out.println("---- \t\t ------------ \t\t ------- \t\t -------");
        checkOutRecord.getCheckOutRecordEntries().forEach(ckrEntry -> {
            System.out.println(ckrEntry.getBookCopy().getIsBn() + " \t\t\t" + ckrEntry.getBookCopy().getCopyNumber() +
                    " \t\t \t\t" +     ckrEntry.getCheckOutDate() + " \t\t" + ckrEntry.getDueDate());
        });
    }


    public static Book searchBookByIsBn(String isBn)  {
        Book book = new Book();
        try {
            DataAccess dataAccess = new DataAccessFacade();

            book = dataAccess.readBooksMap().get(isBn);

        } catch (
                Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);



        }
        return book;
    }

    public static void printAllBooks() {
        HashMap<String, Book>  books = dataAccess.readBooksMap();
        try {
            if (books.size()<1)
                System.out.println(ANSI_RED +"Sorry, no books found \n "+ ANSI_RESET);
            else
            {
                System.out.println("Found " + books.size() + " books");

                books.forEach((isbn,book) -> {
                    System.out.println("-------------------\n");
                    System.out.println("* ISBN: "+ isbn);
                    System.out.println("* Title: "+ book.getTitle());
                    System.out.println("* MaxCheckoutLength: "+ book.getMaxCheckoutLength());
                    System.out.println("* Authors: ") ;
                    book.getAuthors().forEach(author -> {
                        System.out.println("\t" + author.getFirstName() + " "+ author.getLastName());
                    });
                    System.out.println("* Copies: ");
                    book.getBookCopies().forEach(bookCopy -> {
                        System.out.println("\tCopyNumber: " +bookCopy.getCopyNumber() + ", Available:  " + bookCopy.getAvailable());
                    });
                });
                System.out.println("\n-------------------\n");
            }

        } catch (
                Exception e) {
            System.out.println(ANSI_RED +"Sorry Something Wrong happened " + e.getMessage()+"\n"+ ANSI_RESET);
        }
    }
    public static void printAllMembers() {
        HashMap<String, LibraryMember> members = dataAccess.readMemberMap();
        Object[] memberIds = members.keySet().toArray();

        try {
            if (members.size() < 1)
                System.out.println(ANSI_RED + "Sorry, no member found \n " + ANSI_RESET);
            else {
                for (int i = 0; i < memberIds.length; i++) {
                    System.out.println("-------------------\n");
                    System.out.println(ANSI_GREEN  + members.get(memberIds[i]).getMemberId());
                    System.out.println("FirstName: " + members.get(memberIds[i]).getFirstName());
                    System.out.println("LastName: " + members.get(memberIds[i]).getLastName());
                    System.out.println("Phone: " + members.get(memberIds[i]).getPhone());
                    System.out.println("Address: " + members.get(memberIds[i]).getAddress().toString());
                }
                System.out.println("-------------------\n" + ANSI_RESET);
            }

        } catch (
                Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);
        }

    }
    public static LibraryMember searchForMember(String memberId) {
        try {
            DataAccess dataAccess = new DataAccessFacade();
            HashMap<String, LibraryMember> mems = dataAccess.readMemberMap();
            return dataAccess.readMemberMap().get(memberId);
        } catch (
                Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);
            return  null;
        }
    }
    public static void printCheckOutRecord(String memberId) {
        try {
            LibraryMember member = searchForMember(memberId);

            if (member != null) {
                System.out.println("the Member check out history is  \n" + member.getCheckOutRecord().toString());
            } else {

                System.out.println(ANSI_RED + "Sorry : The Member Id  is not Found\n " + ANSI_RESET);
            }

        } catch (Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);
        }
    }

    public static void addBookCopy(String isBn) {
        try {
            Book book = searchBookByIsBn(isBn);
            if (book != null) {
                book.addCopy();
                dataAccess.saveNewBook(book);
                System.out.printf(ANSI_GREEN + "Congratulations add copy operation  done successfully " + "\n " +
                        "now the book details are :- " + book + ANSI_RESET);

            } else {
                System.out.println(ANSI_RED + "Sorry : The Book is not Found \n " + ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);
        }
    }
}