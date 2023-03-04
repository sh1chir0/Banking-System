package ua.sh1chiro.bank_system.models.paymentsTransfers.payments;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ua.sh1chiro.bank_system.auxiliary.Iban;
import ua.sh1chiro.bank_system.models.cards.Card;

/**
 * @author sh1chiro 25.02.2023
 */
@Setter @Getter
public class Payment {
    private Iban type;
    @Min(value = 1, message = "Сума може бути від 1 до 1.000.000 грн.")
    @Max(value = 1000000, message = "Сума може бути від 1 до 1.000.000 грн.")
    private double money;
    @NotEmpty(message = "Поле не може бути пустим")
    @Size(min = 16, max = 16, message = "Номер картки має 16 цифр")
    private String payCard;
    public Payment() {
    }
    public Payment(String payCard, Iban type, double money, Card card) {
        this.payCard = payCard;
        this.type = type;
        this.money = money;
    }
}
