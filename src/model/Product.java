package model;

import java.math.BigDecimal;

public class Product extends AbstractModel {
    private String name;
    private String description; // optional
    private String sku;
    private BigDecimal netPrice;
    private BigDecimal grossPrice;
    private int tax;
    private Dimensions dimensions; // optional
    private Double weight; // optional

    public Product(Integer id, String name, String description, String sku, BigDecimal netPrice, BigDecimal grossPrice, int tax, Dimensions dimensions, Double weight) {
        super(id);
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
        this.tax = tax;
        this.dimensions = dimensions;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }
    public int getTax() {
        return tax;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Double getWeight() {
        return weight;
    }
}
