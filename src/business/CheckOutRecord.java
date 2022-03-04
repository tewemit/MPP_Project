package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckOutRecord implements Serializable {
    private static final long serilialVersionUID = 5147264048973262145L;

    private String memberId;
    private final List<CheckOutRecordEntry> checkOutRecordEntries;


public CheckOutRecord(){
    checkOutRecordEntries=new ArrayList<>();
}


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<CheckOutRecordEntry> getCheckOutRecordEntries() {
        return checkOutRecordEntries;
    }

   public void addCheckOutRecordEntry(CheckOutRecordEntry checkOutRecordEntry){
       checkOutRecordEntries.add(checkOutRecordEntry);
   }

    @Override
    public String toString() {
        return

                "\n  memberId='" + memberId + '\'' +
                "\n  checkOutRecordEntries=" + checkOutRecordEntries;
    }
}
