package business;

import java.time.LocalDate;

public class CheckOutRecordEntry {
    private String isBn;
    private LocalDate checkOutDate;
    private LocalDate actualReturnedDate;
    private BookCopy bookCopy;
    private LocalDate dueDate;
    private LocalDate actualReturnDate;

    public String getIsBn() {
        return isBn;
    }

    public void setIsBn(String isBn) {
        this.isBn = isBn;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getActualReturnedDate() {
        return actualReturnedDate;
    }

    public void setActualReturnedDate(LocalDate actualReturnedDate) {
        this.actualReturnedDate = actualReturnedDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }
}
