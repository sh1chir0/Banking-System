package ua.sh1chiro.bank_system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.models.User;

/**
 * @author sh1chiro 24.02.2023
 */
@Component
public class UsersDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public User getUser(String numberPhone){
        return jdbcTemplate.query("SELECT * FROM users WHERE phone=?", new Object[]{numberPhone},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }
    public void newUser(User user){
        jdbcTemplate.update("INSERT INTO users VALUES(?,?)", user.getPhone(), user.getPassword());
    }
    public String getUserType(){
        return jdbcTemplate.query("SELECT * FROM users WHERE phone=?",
                new Object[]{Authorization.getAuthorizedUser().getPhone()},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null).getType();
    }
}
