package ua.sh1chiro.bank_system.models.cards;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 23.02.2023
 */
@Getter @Setter
public class CardCloseOrder{
    private int id;
    private String number;
    private String date;
    private String cvv;
    private Double money;
    private int order_id;
    public CardCloseOrder(){}
    public CardCloseOrder(int id, String number, String date, String cvv, Double money, int order_id) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.money = money;
        this.order_id = order_id;
    }
}
