package ua.sh1chiro.bank_system.models.deposits;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 25.02.2023
 */
@Setter @Getter
public class Deposit {
    private int id, percent, term, deposit_id;
    private String number, date, cvv;
    private Double money;
    public Deposit(){}
    public Deposit(int id, int percent, int term, int deposit_id, String number, String date, String cvv, Double money) {
        this.id = id;
        this.percent = percent;
        this.term = term;
        this.deposit_id = deposit_id;
        this.number = number;
        this.date = date;
        this.cvv = cvv;
        this.money = money;
    }
}
