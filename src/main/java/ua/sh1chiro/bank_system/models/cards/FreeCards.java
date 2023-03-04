package ua.sh1chiro.bank_system.models.cards;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 20.02.2023
 */
@Getter
@Setter
public class FreeCards {
    private String number;
    private String date;
    private String cvv;
    private String status;

    public FreeCards(String number, String date, String cvv) {
        this.number = number;
        this.date = date;
        this.cvv = cvv;
    }
    public FreeCards(){}
}
