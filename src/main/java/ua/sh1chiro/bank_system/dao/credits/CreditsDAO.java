package ua.sh1chiro.bank_system.dao.credits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.models.credits.Credit;
import ua.sh1chiro.bank_system.models.cards.FreeCards;

import java.util.List;

/**
 * @author sh1chiro 24.02.2023
 */
@Component
public class CreditsDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CreditsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createCredit(int id, FreeCards freeCards, double money){
        jdbcTemplate.update("INSERT INTO credits VALUES(?,?,?,?,?)", id, freeCards.getNumber(),
                freeCards.getDate(), freeCards.getCvv(), money);
    }
    public List<Credit> showCredits(int id){
        return jdbcTemplate.query("SELECT * FROM credits WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Credit.class));
    }
    public Credit showCredit(int credit_id){
        return jdbcTemplate.query("SELECT * FROM credits WHERE credit_id=?", new Object[]{credit_id},
                new BeanPropertyRowMapper<>(Credit.class)).stream().findAny().orElse(null);
    }
}