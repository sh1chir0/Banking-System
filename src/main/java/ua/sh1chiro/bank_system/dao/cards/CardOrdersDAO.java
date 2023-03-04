package ua.sh1chiro.bank_system.dao.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.models.cards.CardOrders;
import ua.sh1chiro.bank_system.models.Client;


import java.util.List;

/**
 * @author sh1chiro 23.02.2023
 */
@Component
public class CardOrdersDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CardOrdersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }
    public List<CardOrders> showOrders(){
        return jdbcTemplate.query("SELECT * FROM card_orders", new BeanPropertyRowMapper<>(CardOrders.class));
    }
    public CardOrders showOrder(int id){
        return jdbcTemplate.query("SELECT * FROM card_orders WHERE order_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(CardOrders.class)).stream().findAny().orElse(null);
    }
    public void orderCard(Client authorizedClient){
       // Client authorizedClient = Authorization.getAuthorizedUser();
        jdbcTemplate.update("INSERT INTO card_orders VALUES(?,?,?)", authorizedClient.getId(), authorizedClient.getName(),
                authorizedClient.getAge());
    }
    public void deleteOrder(int id){
        jdbcTemplate.update("DELETE FROM card_orders WHERE order_id=?", new Object[]{id});
    }
}