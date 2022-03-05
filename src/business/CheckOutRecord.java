package business;

import dataaccess.DataAccessFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CheckOutRecord implements Serializable {
    private static final long serialVersionUID = 5147264048973262145L;
    private static DataAccessFacade dataAccess = new DataAccessFacade(); ;

    private String memberId;
    private final List<CheckOutRecordEntry> checkOutRecordEntries;


public CheckOutRecord(){
    checkOutRecordEntries=new ArrayList<>();

}

    public static AtomicReference<String> getCheckedOutUserByIsbn(String isBn, int copyNumber) {
        HashMap<String, CheckOutRecord> records = dataAccess.readCheckOutRecord();
        if (records == null) {
            return null;
        }
        List<CheckOutRecord> checkOutRecords = records.values().stream().collect(Collectors.toList());
        AtomicReference<String> result = new AtomicReference<>();
        checkOutRecords.forEach(checkOutRecord -> {
            checkOutRecord.getCheckOutRecordEntries().forEach( checkOutRecordEntry -> {
                if (checkOutRecordEntry.getBookCopy().getCopyNumber() == copyNumber &&
                        checkOutRecordEntry.getBookCopy().getIsBn().equals(isBn) ) {
                    result.set(checkOutRecord.getMemberId());
                }
            });
        });

        return result;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public List<CheckOutRecordEntry> getCheckOutRecordEntries() {
        return this.checkOutRecordEntries;
    }

   public void addCheckOutRecordEntry(CheckOutRecordEntry checkOutRecordEntry){
       checkOutRecordEntries.add(checkOutRecordEntry);
   }

   public static List<CheckOutRecordEntry> getCheckoutEntriesByIsbn(String isbn){
      HashMap<String, CheckOutRecord> records = dataAccess.readCheckOutRecord();
       if (records == null) {
           return null;
       }
        List<CheckOutRecord> checkOutRecords = records.values().stream().collect(Collectors.toList());

        List<CheckOutRecordEntry> checkOutRecordEntries = checkOutRecords.stream().map(CheckOutRecord::getCheckOutRecordEntries)
                .collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
       List<CheckOutRecordEntry> result = new ArrayList<>();
        checkOutRecordEntries.forEach(checkOutRecordEntry -> {
           if (checkOutRecordEntry.getIsBn().equals(isbn)) {
               result.add(checkOutRecordEntry);
           }
       });
        return  result;
    }

    @Override
    public String toString() {
        return

                "\n  memberId='" + memberId + '\'' +
                "\n  checkOutRecordEntries=" + checkOutRecordEntries;
    }
}
