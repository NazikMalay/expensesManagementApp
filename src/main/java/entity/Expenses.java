package entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nazar on 22.05.17.
 */
@Entity
@Table(name = "EXPENSES")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "DATE", nullable = false)
    private Date date;
    @Column(name = "PRICE", nullable = false)
    private  Double price;
    @Column(name = "CURRENCY", nullable = false)
    private String currency;
    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    public Expenses(Date date, Double price, String currency, String productName) {
        this.date = date;
        this.price = price;
        this.currency = currency;
        this.productName = productName;
    }

    public Expenses() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double amount) {
        this.price = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Expenses{" +
                "date=" + date +
                ", amount=" + price +
                ", currency='" + currency + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
