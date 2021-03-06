package business;

import java.io.Serializable;

public class LibraryMember extends Person implements Serializable {
    private static final long serialVersionUID = 5147265048973263145L;
    private String memberId;
    private CheckOutRecord checkOutRecord;

    public LibraryMember() {
    }

    @Override
    public String toString() {
        return  "\n   memberId='" + memberId + '\'' +
                super.toString();
                //checkOutRecord!=null?      "\n   checkOutRecord=" + checkOutRecord.toString() :"";

    }



    public LibraryMember(String memberId, String firstName, String lastName, String phone, Address address) {
        super(firstName, lastName, phone, address);
        this.memberId = memberId;
        checkOutRecord =new CheckOutRecord();


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
