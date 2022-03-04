package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutRecordEntry implements Serializable {
    private static final long serilialVersionUID = 5147665048973262145L;
    private String id;
    private LocalDate checkOutDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private BookCopy bookCopy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }


}
