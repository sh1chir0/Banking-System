package ua.sh1chiro.bank_system.models.credits;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 24.02.2023
 */
@Setter
@Getter
public class Credit {
    private int id;
    private String number;
    private String date;
    private String cvv;
    private double money;
    private int credit_id;
    public Credit(){}
    public Credit(int id, String number, String date, String cvv, double money, int credit_id) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.money = money;
        this.credit_id = credit_id;
    }
}
