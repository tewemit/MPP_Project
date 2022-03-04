package business;

import java.io.Serializable;

public class BookCopy implements Serializable {
    private static final long   serialVersionUID = 5137265048973262145L;
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

    public String getIsBn() {
        return isBn;
    }
}
