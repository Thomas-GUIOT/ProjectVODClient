import java.io.Serializable;

public class Bill implements Serializable {
    String movieName;
    Integer outrageousPrice;

    public Bill(Integer i) {
        this.outrageousPrice = i;
    }
}
