package ua.sh1chiro.bank_system.dao.credits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.models.credits.CreditOrders;

import java.util.List;

/**
 * @author sh1chiro 24.02.2023
 */
@Component
public class CreditOrdersDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CreditOrdersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createOrder(int id, CreditOrders creditOrders){
        jdbcTemplate.update("INSERT INTO credit_orders VALUES(?,?,?,?,?,?)", id, creditOrders.isJob(),
                creditOrders.isCar(), creditOrders.isRealty(), creditOrders.getChildren(), creditOrders.getMoney());
    }
    public List<CreditOrders> showOrders(){
        return jdbcTemplate.query("SELECT * FROM credit_orders",
                new BeanPropertyRowMapper<>(CreditOrders.class));
    }
    public CreditOrders showOrder(int id){
        return jdbcTemplate.query("SELECT * FROM credit_orders WHERE order_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(CreditOrders.class)).stream().findAny().orElse(null);
    }
    public void deleteOrder(int id){
        jdbcTemplate.update("DELETE FROM credit_orders WHERE order_id=?", new Object[]{id});
    }

}
