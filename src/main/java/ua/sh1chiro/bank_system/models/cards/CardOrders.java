package ua.sh1chiro.bank_system.models.cards;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 23.02.2023
 */
@Getter
@Setter
public class CardOrders {
    private int id;
    private String name;
    private int age;
    private int order_id;
    public CardOrders(){}
    public CardOrders(int id, String name, int age, int order_id) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.order_id = order_id;
    }
}
