package controller;

import business.*;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import exceptions.SystemExceptions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

            if (book != null) {
                copyBook = book.checkAvailable(isBn);
                if (copyBook == null) {
                    System.out.println("Sorry : There is no Book Copy Available ");
                } else {
                    LibraryMember member = dataAccess.readMemberMap().get(memberId);
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
                    dataAccess.saveNewMember(member);


                    System.out.printf(ANSI_GREEN + "Congratulations check out operation  done successfully " + "\n"
                            + checkOutRecordEntry + ANSI_RESET);
                    //   System.out.println("all checout records for this Member " + memberId + "\n are :- " + checkOutRecord);
                }

            } else {
                System.out.println(ANSI_RED + "Sorry : The Book is not Found \n " + ANSI_RESET);
            }

        } catch (Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n " + ANSI_RESET);
        }

    }

    public static void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
        Book book = new Book(isbn, title, maxCheckoutLength, authors);
        dataAccess.saveNewBook(book);
        System.out.printf(ANSI_GREEN + "Booked added successfully.\n" + ANSI_RESET);

       searchAndShowBook(book.getIsBn());

    }

    public static String searchAndShowBook(String isbn) {
        Book book = dataAccess.readBooksMap().get(isbn);
        if (book == null) {
            return "No book found with ISBN: " + isbn;
        }
        System.out.println("================||Book details for " + isbn + " ||================");
        System.out.println("ISBN \t\t CopyNo \t\t Title \t\t CheckoutDate \t\t DueDate \t\t CheckedOutBy");
        System.out.println("---- \t\t ------------ \t\t -------");
        System.out.println("\n**" + book.getIsBn() + "\t\t** Title: " + book.getTitle());
        List<CheckOutRecord> checkOutRecords = dataAccess.readMemberMap().values()
                .stream().map(LibraryMember::getCheckOutRecord).collect(Collectors.toList());

        book.getBookCopies().forEach(bookCopy -> {

            CheckOutRecordEntry checkOutRecordEntries = (CheckOutRecordEntry) checkOutRecords.stream().map(CheckOutRecord::getCheckOutRecordEntries)
                    .map(checkOutRecordEntries1 -> checkOutRecordEntries1.stream().filter(cf -> cf.getBookCopy() == bookCopy))
                    .map(checkOutRecordEntryStream -> checkOutRecordEntryStream.
                            anyMatch(checkOutRecordEntry -> checkOutRecordEntry.getBookCopy() == bookCopy));

            System.out.println(bookCopy.getIsBn() + "\t\t" + bookCopy.getCopyNumber() + "\t\t" + book.getTitle()
                    + "\t\t" + checkOutRecords.stream().map(CheckOutRecord::getCheckOutRecordEntries)
                    .map(checkOutRecordEntries1 -> checkOutRecordEntries1.stream().filter(cf -> cf.getBookCopy() == bookCopy))
                    .map(checkOutRecordEntryStream -> checkOutRecordEntryStream.
                            anyMatch(checkOutRecordEntry -> checkOutRecordEntry.getBookCopy() == bookCopy)));

        });
        return
                "\n** Title: " + book.getTitle() +
                        "\n** ISBN: " + book.getIsBn();
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
            System.out.println("Choose option below for the member: ");
            System.out.println("8 Show checkout records");
            return true;
        }
    }

    public static void showMemberCheckoutRecords(String memberId) {
        CheckOutRecord checkOutRecord = dataAccess.readMemberMap().get(memberId).getCheckOutRecord();
        if (checkOutRecord == null) {
            System.out.println(ANSI_RED + "No checkout record found for member:" + memberId + "\n " + ANSI_RESET);

            return;
        }
        checkOutRecord.getCheckOutRecordEntries().forEach(ckrEntry -> {
            System.out.println("ISBN \t\t CheckoutDate \t\t DueDate");
            System.out.println("---- \t\t ------------ \t\t -------");
            System.out.println("|" + ckrEntry.getBookCopy().getIsBn() + "| \t\t" +
                    ckrEntry.getCheckOutDate() + "| \t\t" + ckrEntry.getDueDate());
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
        HashMap<String, Book> books = dataAccess.readBooksMap();
        Object[] bookIsBns = books.keySet().toArray();

        try {
            if (books.size() < 1)
                System.out.println(ANSI_RED + "Sorry : There is no books found \n " + ANSI_RESET);
            else {
                for (int i = 0; i < bookIsBns.length; i++) {
                    System.out.println(books.get(bookIsBns[i]));
                    System.out.println("-------------------\n");

                }
            }

        } catch (
                Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);


        }

    }
    public static void printAllMembers() {
        HashMap<String, LibraryMember> members = dataAccess.readMemberMap();
        Object[] memberIds = members.keySet().toArray();

        try {
            if (members.size() < 1)
                System.out.println(ANSI_RED + "Sorry : There is no members found \n " + ANSI_RESET);
            else {
                for (int i = 0; i < memberIds.length; i++) {
                    System.out.println(members.get(memberIds[i]).toString());
                    System.out.println("-------------------\n");

                }
            }

        } catch (
                Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);


        }

    }
    public static LibraryMember searchForMember(String memberId) {
        LibraryMember member = new LibraryMember();
        try {
            DataAccess dataAccess = new DataAccessFacade();

            HashMap<String, LibraryMember> mems = dataAccess.readMemberMap();

            member = dataAccess.readMemberMap().get(memberId);

        } catch (
                Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);

        }
        return member;
    }

    public static void printCheckOutRecord(String memberId) {
        try {
            boolean found = false;

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
                System.out.printf(ANSI_GREEN + "Congratulations add copy operation  done successfully " + "\n now the book details are :- " + book + ANSI_RESET);


            } else {


                System.out.println(ANSI_RED + "Sorry : The Book is not Found \n " + ANSI_RESET);
            }
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Sorry Something Wrong happened " + e.getMessage() + "\n" + ANSI_RESET);
        }
    }
}