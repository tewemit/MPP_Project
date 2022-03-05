package business;

import dataaccess.DataAccessFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CheckOutRecord implements Serializable {
    private static final long serialVersionUID = 5147264048973262145L;
    private static DataAccessFacade dataAccess = new DataAccessFacade();
    ;
    private final List<CheckOutRecordEntry> checkOutRecordEntries;
    private String memberId;


    public CheckOutRecord() {
        checkOutRecordEntries = new ArrayList<>();

    }

    public static String getWhoCheckedOutACopy(String isBn, int copyNumber) {
        HashMap<String, CheckOutRecord> records = dataAccess.readCheckOutRecord();
        if (records == null) {
            return null;
        }
        AtomicReference<String> currentMemberID = new AtomicReference<>();
        AtomicBoolean found = new AtomicBoolean(false);
        records.forEach((memberId, checkOutRecord) -> {
            currentMemberID.set(memberId);
            checkOutRecord.checkOutRecordEntries.forEach(checkOutRecordEntry -> {
                if (checkOutRecordEntry.getBookCopy().getCopyNumber() == copyNumber &&
                        checkOutRecordEntry.getBookCopy().getIsBn().equals(isBn)) {
                    found.set(true);
                }
            });
        });
        return found.get() ? currentMemberID.get() : null;
    }

    public static List<CheckOutRecordEntry> getCheckoutEntriesByIsbn(String isbn) {
        HashMap<String, CheckOutRecord> records = dataAccess.readCheckOutRecord();
        if (records == null) {
            return null;
        }
        List<CheckOutRecord> checkOutRecords = records.values().stream().collect(Collectors.toList());

        List<CheckOutRecordEntry> checkOutRecordEntries = checkOutRecords.stream().map(CheckOutRecord::getCheckOutRecordEntries)
                .collect(Collectors.toList()).stream().flatMap(List::stream).collect(Collectors.toList());
        List<CheckOutRecordEntry> result = new ArrayList<>();
        checkOutRecordEntries.forEach(checkOutRecordEntry -> {
            if (checkOutRecordEntry.getBookCopy().getIsBn().equals(isbn)) {
                result.add(checkOutRecordEntry);
            }
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

    public void addCheckOutRecordEntry(CheckOutRecordEntry checkOutRecordEntry) {
        checkOutRecordEntries.add(checkOutRecordEntry);
    }

    @Override
    public String toString() {
        return

                "\n  memberId='" + memberId + '\'' +
                        "\n  checkOutRecordEntries=" + checkOutRecordEntries;
    }
}
