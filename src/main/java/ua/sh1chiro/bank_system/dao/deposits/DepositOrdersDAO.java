package ua.sh1chiro.bank_system.dao.deposits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.models.deposits.DepositOrder;

import java.util.List;

/**
 * @author sh1chiro 25.02.2023
 */
@Component
public class DepositOrdersDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DepositOrdersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createOrder(int id, DepositOrder depositOrder){
        jdbcTemplate.update("INSERT INTO deposit_orders VALUES(?,?, ?)", id, depositOrder.getMoney(),
                depositOrder.getTerm());
    }
    public List<DepositOrder> showOrders(){
        return jdbcTemplate.query("SELECT * FROM deposit_orders", new BeanPropertyRowMapper<>(DepositOrder.class));
    }
    public DepositOrder showOrder(int deposit_id){
        return jdbcTemplate.query("SELECT * FROM deposit_orders WHERE deposit_id = ?", new Object[]{deposit_id},
                new BeanPropertyRowMapper<>(DepositOrder.class)).stream().findAny().orElse(null);
    }
    public void deleteOrder(int id){
        jdbcTemplate.update("DELETE FROM deposit_orders WHERE deposit_id=?", new Object[]{id});
    }
}
