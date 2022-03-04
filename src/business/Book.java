package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private static final long serialVersionUID = 5147265048973262104L;
    private String isBn;
    private String title;
    private List<Author> authors;
    private int maxCheckoutLength;
    List<BookCopy> bookCopies;

    public String getIsBn() {
        return isBn;
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

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public Book(String isbn, String title, int maxCheckoutLength, List<Author> authors) {

        this.isBn = isbn;
        this.title = title;
        this.authors = authors;
        this.maxCheckoutLength = maxCheckoutLength;
        this.bookCopies = new ArrayList<>();
    }

    public void addCopy() {
        BookCopy copyBook = new BookCopy();
            copyBook.setCopyNumber(bookCopies.size()+1);

            copyBook.setCopyNumber(1);

        copyBook.setId(isBn);
        bookCopies.add(copyBook);

    }
}
