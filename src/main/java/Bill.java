import java.io.Serializable;

public class Bill implements Serializable {
    String isbn;
    Integer outrageousPrice;

    public Bill(String isbn, Integer outrageousPrice) {
        this.isbn = isbn;
        this.outrageousPrice = outrageousPrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getOutrageousPrice() {
        return outrageousPrice;
    }

    public void setOutrageousPrice(Integer outrageousPrice) {
        this.outrageousPrice = outrageousPrice;
    }
}
