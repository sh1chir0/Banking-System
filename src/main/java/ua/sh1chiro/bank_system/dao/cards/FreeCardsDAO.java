package ua.sh1chiro.bank_system.dao.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.models.cards.FreeCards;

/**
 * @author sh1chiro 22.02.2023
 */
@Component
public class FreeCardsDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FreeCardsDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public FreeCards getFreeCards(){
        return jdbcTemplate.query("SELECT * FROM free_cards WHERE status='free' ORDER BY number",
                new BeanPropertyRowMapper<>(FreeCards.class)).stream().findAny().orElse(null);
    }
    public void changeStatus(FreeCards freeCards){
        jdbcTemplate.update("UPDATE free_cards SET number = ?, date = ?, cvv = ?, status = ? WHERE number = ?",
                freeCards.getNumber(), freeCards.getDate(), freeCards.getCvv(), "busy", freeCards.getNumber());
    }
}