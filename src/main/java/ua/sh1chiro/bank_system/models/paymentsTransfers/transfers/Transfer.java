package ua.sh1chiro.bank_system.models.paymentsTransfers.transfers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 25.02.2023
 */
@Setter @Getter
public class Transfer {
    @Min(value = 1, message = "Сума може бути від 0 до 1.000.000 грн.")
    @Max(value = 1000000, message = "Сума може бути від 0 до 1.000.000 грн.")
    private double money;
    @NotEmpty(message = "Поле не може бути пустим")
    private String payCard;
    @NotEmpty(message = "Поле не може бути пустим")
    private String cardToPay;
    public Transfer(){}
    public Transfer(double money, String payCard, String cardToPay) {

        this.payCard = payCard;
        this.money = money;
        this.cardToPay = cardToPay;
    }
}
