package business;

import java.util.List;

public class Book {

    private final String isBn;
    private final String title;
    private final List<Author> authors;
    private final int maxCheckoutLength;
    List<BookCopy> bookCopies;

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

    public Book(String isBn, String title, int maxCheckoutLength, List<Author> authors) {

        this.isBn = isBn;
        this.title = title;
        this.authors = authors;
        this.maxCheckoutLength = maxCheckoutLength;
    }

    public void addCopy() {
        BookCopy copyBook = new BookCopy();
        copyBook.setCopyNumber(bookCopies.size() + 1);
        copyBook.setIsBn(this.isBn);
        copyBook.setAvailable(true);
        bookCopies.add(copyBook);

    }
}
