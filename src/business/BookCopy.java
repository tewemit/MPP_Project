package business;

import java.io.Serializable;

public class BookCopy implements Serializable {
    private static final long   serialVersionUID = 5137265048973262145L;
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

    @Override
    public String toString() {
        return "" +
                "\n   isBn='" + isBn + '\'' +
                "\n   copyNumber=" + copyNumber +
                "\n   isAvailable=" + isAvailable
                ;
    }

    public boolean getAvailable() {
        return this.isAvailable;
    }
}
