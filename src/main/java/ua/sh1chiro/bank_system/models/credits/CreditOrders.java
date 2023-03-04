package ua.sh1chiro.bank_system.models.credits;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 24.02.2023
 */
@Getter @Setter
public class CreditOrders {
    private int id;
    private boolean job;
    private boolean car;
    private boolean realty;
    @Max(value = 20, message = "Вкажіть корректну кількість")
    private int children;
    private int order_id;
    @Min(value = 1, message = "Не менше 1000 грн.")
    @Max(value = 100000, message = "Не більше 100.000 грн.")
    private double money;
    public CreditOrders(){}
    public CreditOrders(int id, boolean job, boolean car, boolean realty, int children, int order_id, double money) {
        this.id = id;
        this.job = job;
        this.car = car;
        this.realty = realty;
        this.children = children;
        this.order_id = order_id;
        this.money=money;
    }
}
