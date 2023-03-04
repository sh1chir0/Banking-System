package ua.sh1chiro.bank_system.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.models.Client;

import java.util.List;

/**
 * @author sh1chiro 19.02.2023
 */
@Component
public class ClientDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public ClientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Client showClient(int id){
        return jdbcTemplate.query("SELECT * FROM Client WHERE client_id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Client.class)).stream().findAny().orElse(null);
    }
    public Client showClient(String phone){
        return jdbcTemplate.query("SELECT * FROM Client WHERE phone=?", new Object[]{phone},
                new BeanPropertyRowMapper<>(Client.class)).stream().findAny().orElse(null);
    }
    public void newClient(Client client){
        jdbcTemplate.update("INSERT INTO Client VALUES(?,?,?,?)", client.getId(), client.getName(), client.getAge(),
                client.getPhone());
    }
    public boolean verified(){
        Client client = jdbcTemplate.query("SELECT * FROM client WHERE phone=?", new Object[]{Authorization.getAuthorizedUser().getPhone()},
                new BeanPropertyRowMapper<>(Client.class)).stream().findAny().orElse(null);
        if(client != null)
            return client.isVerified();
        else
            return false;
    }
    public void approveVerification(int id){
        Client client = showClient(id);
        jdbcTemplate.update("UPDATE client SET id=?, name=?, age=?, phone=?, verified='true' WHERE client_id=?",
                client.getId(), client.getName(), client.getAge(), client.getPhone(), id);
    }
    public void refuseVerification(int id){
        jdbcTemplate.update("DELETE FROM client WHERE client_id=?", new Object[]{id});
    }
    public List<Client> showUnverified(){
        return jdbcTemplate.query("SELECT * FROM client WHERE verified=false", new BeanPropertyRowMapper<>(Client.class));
    }
}