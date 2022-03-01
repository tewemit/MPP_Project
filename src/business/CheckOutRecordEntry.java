package business;

import java.time.LocalDate;

public class CheckOutRecordEntry {
    private String id;
    private LocalDate checkOutDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private BookCopy bookCopy;
}
