package business;

import dataaccess.DataAccessFacade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private static final long serialVersionUID = 5147265048973262104L;
    List<BookCopy> bookCopies;
    private String isBn;
    private String title;
    private List<Author> authors;
    private int maxCheckoutLength;


    public Book() {
    }

    public Book(String isBn, String title, int maxCheckoutLength, List<Author> authors) {

        this.isBn = isBn;
        this.title = title;
        this.authors = authors;
        this.maxCheckoutLength = maxCheckoutLength;
        this.bookCopies = new ArrayList<>();
    }

    public String getIsBn() {
        return isBn;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public String getTitle() {
        return title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public int getMaxCheckoutLength() {
        return maxCheckoutLength;
    }

    @Override
    public String toString() {
        return
                "\n  isBn='" + isBn + '\'' +
                        "\n  title='" + title + '\'' +
                        "\n  authors=" + authors.toString() +
                        "\n  maxCheckoutLength=" + maxCheckoutLength +
                        "\n  bookCopies=" + bookCopies.toString()
                ;
    }

    public BookCopy getAvailableCopy(String isBn) {

        for (BookCopy c : bookCopies) {
            if (c.isAvailable == true)
                return c;
        }
        return null;
    }

    public void addCopy() {
        BookCopy copyBook = new BookCopy();
        copyBook.setCopyNumber(bookCopies.size() + 1);
        copyBook.setIsBn(this.isBn);
        copyBook.setAvailable(true);
        bookCopies.add(copyBook);
    }


    public static String searchAndShowBook(String isbn) {
        final DataAccessFacade dataAccess = new DataAccessFacade();
        StringBuilder stringBuilder = new StringBuilder();
        Book book = dataAccess.readBooksMap().get(isbn);
        if (book == null) {
            stringBuilder.append("No book found with ISBN:" + isbn + "\n");
            return stringBuilder.toString();
        }
        stringBuilder.append("===========================||Book details for " + isbn + " ||======================================\n" );
        stringBuilder.append("ISBN \t|\t CopyNo \t|\t Title \t|\t\t\t CheckoutDate \t|\t DueDate \t|\t CheckedOutBy \t|\t OverDue(Yes/No)\n");
        stringBuilder.append("---- \t|\t ------- \t|\t------ \t|\t\t\t------------ \t|\t ------- \t|\t ------------\t|\t ------------\n");
        List<BookCopy> copies = book.getBookCopies();
        List<CheckOutRecordEntry> entries = CheckOutRecord.getCheckoutEntriesByIsbn(book.getIsBn());

        for (BookCopy copy : copies) {
            entries.forEach(checkOutRecordEntry -> {
                String memberId = "";
                if (CheckOutRecord.getWhoCheckedOutACopy(book.getIsBn(), copy.getCopyNumber()) != null) {
                    memberId = CheckOutRecord.getWhoCheckedOutACopy(book.getIsBn(), copy.getCopyNumber());
                }
                if (checkOutRecordEntry.getBookCopy().getCopyNumber() == copy.getCopyNumber()) {
                    boolean isOverdue = (checkOutRecordEntry.getDueDate().isBefore(LocalDate.now()) && !copy.getAvailable());
                    stringBuilder.append(copy.getIsBn() + "\t\t| \t" + copy.getCopyNumber() +
                            "\t\t\t| \t" + book.getTitle() +
                            "\t\t| \t" + checkOutRecordEntry.getCheckOutDate() + "\t\t| \t" + checkOutRecordEntry.getDueDate()
                            + "\t\t| \t\t" + memberId + "\t\t| \t\t" + (isOverdue?"Yes":"No" ) + "\n");
                }
            });
        }
        return stringBuilder.toString();
    }
}
