package business;

public class LibraryMember extends Person {
    private String memberId;
    private CheckOutRecord checkOutRecord;

    public LibraryMember(String memberId, String firstName, String lastName, String phone, Address address) {
        super(firstName, lastName, phone, address);
        this.memberId = memberId;

    }

    public CheckOutRecord getCheckOutRecord() {
        return checkOutRecord;
    }

    public String getMemberId() {
        return memberId;
    }


    public void setCheckOutRecord(CheckOutRecord checkOutRecord) {
        this.checkOutRecord = checkOutRecord;
    }
}
