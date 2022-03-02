package business;

public class BookCopy {
    private String isBn;
    private int copyNumber;
    boolean isAvailable;

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getIsBn() {
        return isBn;
    }

    public void setIsBn(String isBn) {
        this.isBn = isBn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCopyNumber() {
        return copyNumber;
    }

     void setCopyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
    }


}
