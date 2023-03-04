package ua.sh1chiro.bank_system.dao.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.models.cards.Card;
import ua.sh1chiro.bank_system.models.cards.CardCloseOrder;

import java.util.List;

/**
 * @author sh1chiro 23.02.2023
 */
@Component
public class CardCloseOrdersDAO {
    private final JdbcTemplate jdbcTemplate;
    private final CardDAO cardDAO;
    @Autowired
    public CardCloseOrdersDAO(JdbcTemplate jdbcTemplate, CardDAO cardDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.cardDAO = cardDAO;
    }
    public void closeCard(String number){
        Card card = cardDAO.showCard(number);
        jdbcTemplate.update("INSERT INTO card_close_orders VALUES(?,?,?,?,?)", card.getClient_id(),
                card.getNumber(), card.getDate(), card.getCvv(), card.getMoney());
    }
    public List<CardCloseOrder> showOrders(){
        return jdbcTemplate.query("SELECT * FROM card_close_orders", new BeanPropertyRowMapper<>(CardCloseOrder.class));
    }
    public CardCloseOrder showOrder(int id){
        return jdbcTemplate.query("SELECT * FROM card_close_orders WHERE order_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(CardCloseOrder.class)).stream().findAny().orElse(null);
    }
    public void deleteOrder(int id){
        jdbcTemplate.update("DELETE FROM card_close_orders WHERE order_id=?", new Object[]{id});
    }

}
