package model;

public class Product extends AbstractModel {
    private String name;
    private String description; // optional
    private String sku;
    private double netPrice;
    private double grossPrice;
    private Dimensions dimensions; // optional
    private Double weight; // optional


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Double getWeight() {
        return weight;
    }
}
