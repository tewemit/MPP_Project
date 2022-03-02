package business;

import java.util.List;

public class CheckOutRecord {
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
