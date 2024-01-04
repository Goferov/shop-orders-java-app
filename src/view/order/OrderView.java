package view.order;

import model.Order;
import view.AbstractView;

public class OrderView extends AbstractView {

    public OrderView() {
        super(new String[]{"ID", "Data złożenia", "Cena", "Zamawiający"});
    }
    @Override
    public void addToView(Object o) {
        Order order  = (Order) o;
        tableModel.addRow(new Object[]{order.getId(), order.getDate(), order.getOrderPrice(), order.getClient()});
    }
}
