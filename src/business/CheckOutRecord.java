package business;

import java.io.Serializable;
import java.util.List;

public class CheckOutRecord implements Serializable {
    private static final long serilialVersionUID = 5147264048973262145L;
    private String isBn;
    private String memberId;
    private List<CheckOutRecordEntry> checkOutRecordEntries;

    public String getIsBn() {
        return isBn;
    }

    public void setIsBn(String isBn) {
        this.isBn = isBn;
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
}
