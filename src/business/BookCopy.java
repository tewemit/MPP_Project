package business;

public class BookCopy {
    private String isBn;
    private int copyNumber;
    boolean isAvailable;

    public String getId() {
        return isBn;
    }

    public void setId(String id) {
        this.isBn = id;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

     void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }


}
