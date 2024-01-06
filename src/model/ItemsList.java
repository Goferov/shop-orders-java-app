package model;

import java.math.BigDecimal;

public class ItemsList extends AbstractModel {
    private int quantity;
    private String name;
    private int discount; // optional
    private BigDecimal netTotal;
    private BigDecimal grossTotal;

    public ItemsList(Integer id, int quantity, String name, int discount, BigDecimal netTotal, BigDecimal grossTotal) {
        super(id);
        this.quantity = quantity;
        this.name = name;
        this.discount = discount;
        this.netTotal = netTotal;
        this.grossTotal = grossTotal;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public int getDiscount() {
        return discount;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }
}
