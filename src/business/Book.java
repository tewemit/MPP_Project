package business;

import java.util.List;

public class Book {

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

    public Book(String isb, String title, int maxCheckoutLength, List<Author> authors) {

        this.isBn = isBn;
        this.title = title;
        this.authors = authors;
        this.maxCheckoutLength = maxCheckoutLength;
    }

    public void addCopy() {
        BookCopy copyBook = new BookCopy();
        copyBook.setCopyNumber(bookCopies.size()+1);
        copyBook.setId(this.isBn);
        bookCopies.add(copyBook);

    }
}
