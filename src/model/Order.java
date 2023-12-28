package model;
import java.util.Date;
import java.util.List;
public class Order {
    private int id;
    private Date date;
    private List<ItemsList> ItemsList;
    private Customer client;
    private Address deliveryAddress;
}
