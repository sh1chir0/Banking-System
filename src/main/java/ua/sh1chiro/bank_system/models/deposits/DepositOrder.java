package ua.sh1chiro.bank_system.models.deposits;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 25.02.2023
 */
@Setter @Getter
public class DepositOrder {
    private int id;
    @Min(value = 1000, message = "Сума не може бути менше 1000 грн.")
    @Max(value = 100000, message = "Сума не може бути більше 100.000 грн.")
    private double money;
    private double percent;
    @Min(value = 1, message = "Термін може бути від 1 до 24 місяців")
    @Max(value = 24, message = "Термін може бути від 1 до 24 місяців")
    private int term;
    private int deposit_id;
    public DepositOrder(){}
    public DepositOrder(int id, double money, double percent,int term, int deposit_id) {
        this.id = id;
        this.money = money;
        this.percent = percent;
        this.term = term;
        this.deposit_id = deposit_id;
    }
}
