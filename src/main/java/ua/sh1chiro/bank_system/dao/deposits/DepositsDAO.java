package ua.sh1chiro.bank_system.dao.deposits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.models.cards.FreeCards;
import ua.sh1chiro.bank_system.models.deposits.Deposit;
import ua.sh1chiro.bank_system.models.deposits.DepositOrder;

import java.util.List;

/**
 * @author sh1chiro 25.02.2023
 */
@Component
public class DepositsDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DepositsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createDeposit(FreeCards freeCards, DepositOrder depositOrder){
        jdbcTemplate.update("INSERT INTO deposits VALUES(?,?,?,?,?,?,?)", depositOrder.getId(), freeCards.getNumber(),
                freeCards.getDate(), freeCards.getCvv(), depositOrder.getMoney(), depositOrder.getPercent(), depositOrder.getTerm());
    }
    public List<Deposit> showDeposits(int client_id){
        return jdbcTemplate.query("SELECT * FROM deposits WHERE id=?", new Object[]{client_id},
                new BeanPropertyRowMapper<>(Deposit.class));
    }
    public Deposit showDeposit(int deposit_id){
        return jdbcTemplate.query("SELECT * FROM deposits WHERE deposit_id=?", new Object[]{deposit_id},
                new BeanPropertyRowMapper<>(Deposit.class)).stream().findAny().orElse(null);
    }
    public Deposit deposit(int client_id){
        return jdbcTemplate.query("SELECT * FROM deposits WHERE id=?", new Object[]{client_id},
                new BeanPropertyRowMapper<>(Deposit.class)).stream().findAny().orElse(null);

    }
    public boolean isDeposit(){
        if(deposit(Authorization.getAuthorizedClient().getId()) != null)
            return true;
        else
            return false;
    }

    /**
     * Метод, який нараховує відсотки по депозиту.
     * Бере з бази даних депозит авторизованого юзера та нараховує йому відсотки до загальної суми.
     */
    public void calculatePercents(){
        try{
            Deposit deposit = deposit(Authorization.getAuthorizedClient().getId());
            double money = deposit.getMoney();
            String number = deposit.getNumber();
            double percent = deposit.getPercent();
            double moneyWithPercents = (money*percent/100) + money;
            jdbcTemplate.update("UPDATE deposits SET money = ? WHERE number = ?", moneyWithPercents, number);
        }catch(Exception ex){}
    }
}
