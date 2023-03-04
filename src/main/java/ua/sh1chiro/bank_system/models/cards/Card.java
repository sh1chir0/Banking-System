package ua.sh1chiro.bank_system.models.cards;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 22.02.2023
 */
@Getter @Setter
public class Card {
    private int client_id;
    private String number;
    private String date;
    private String cvv;
    private double money;
    private String status;
    public Card(){}
    public Card(int client_id, String number, String date, String cvv, double money, String status) {
        this.client_id = client_id;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.money = money;
        this.status = status;
    }
}