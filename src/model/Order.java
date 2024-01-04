package model;
import java.util.Date;
import java.util.List;
public class Order extends AbstractModel {
    private Date date;
    private List<ItemsList> ItemsList;
    private Customer client;
    private Address deliveryAddress;

    public Order(Integer id, Date date, List<model.ItemsList> itemsList, Customer client, Address deliveryAddress) {
        super(id);
        this.date = date;
        ItemsList = itemsList;
        this.client = client;
        this.deliveryAddress = deliveryAddress;
    }

    public Date getDate() {
        return date;
    }

    public List<model.ItemsList> getItemsList() {
        return ItemsList;
    }

    public Customer getClient() {
        return client;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getOrderPrice() {
        return 0;
    }
}
