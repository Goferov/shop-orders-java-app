package view.order;

import model.Order;
import util.DateTimeUtil;
import view.AbstractView;

public class OrderView extends AbstractView {

    public OrderView() {
        super(new String[]{"ID", "Data złożenia", "Cena", "Zamawiający"});
    }
    @Override
    public void addToView(Object o) {
        Order order  = (Order) o;
        tableModel.addRow(new Object[]{order.getId(), DateTimeUtil.showDate(order.getDate()), order.getOrderTotalPrice(), order.getClient()});
    }
}
