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
    public Book() {
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

    public Book(String isb, String title, int maxCheckoutLength, List<Author> authors) {

        this.isBn = isBn;
        this.title = title;
        this.authors = authors;
        this.maxCheckoutLength = maxCheckoutLength;
        this.bookCopies = new ArrayList<>();
    }
    public BookCopy checkAvailable(String isBn) {
        BookCopy bookCopy=new BookCopy();
        int copyNumber = -1;
        for (BookCopy c : bookCopies) {
            if (c.isAvailable == true)
                return c;
        }
        return null;
    }
    public void addCopy() {
        BookCopy copyBook = new BookCopy();
        
        copyBook.setCopyNumber(bookCopies.size()+1);
        copyBook.setIsBn(this.isBn);
        copyBook.setAvailable(true);
        bookCopies.add(copyBook);

    }
}
