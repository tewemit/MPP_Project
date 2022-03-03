package business;

import java.io.Serializable;
import java.util.List;

public class CheckOutRecord implements Serializable {
    private static final long serilialVersionUID = 5147264048973262145L;
    private String id;
    private String memberId;
    private List<CheckOutRecordEntry> checkOutRecordEntries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<CheckOutRecordEntry> getCheckOutEntries() {
        return checkOutEntries;
    }

    public void setCheckOutEntries(List<CheckOutRecordEntry> checkOutEntries) {
        this.checkOutEntries = checkOutEntries;
    }

    private List<CheckOutRecordEntry> checkOutEntries;
}
