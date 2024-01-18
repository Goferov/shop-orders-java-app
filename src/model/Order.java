package model;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
public class Order extends AbstractModel {
    private Date date;
    private List<ItemsList> itemsList;
    private Customer client;
    private Address deliveryAddress;

    public Order(Integer id, Date date, List<model.ItemsList> itemsList, Customer client, Address deliveryAddress) {
        super(id);
        this.date = date;
        this.itemsList = itemsList;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
    }

    public Date getDate() {
        return date;
    }

    public List<model.ItemsList> getItemsList() {
        return itemsList;
    }

    public Customer getClient() {
        return client;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public BigDecimal getOrderTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemsList item : itemsList) {
            BigDecimal itemTotal = item.getGrossTotal();

            if (item.getDiscount() > 0) {
                BigDecimal discount = BigDecimal.valueOf(item.getDiscount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                itemTotal = itemTotal.subtract(itemTotal.multiply(discount));
            }

            total = total.add(itemTotal);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
