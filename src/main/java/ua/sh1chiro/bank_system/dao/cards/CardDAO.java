package ua.sh1chiro.bank_system.dao.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import ua.sh1chiro.bank_system.models.cards.Card;
import ua.sh1chiro.bank_system.models.cards.FreeCards;
import ua.sh1chiro.bank_system.models.paymentsTransfers.payments.Payment;
import ua.sh1chiro.bank_system.models.paymentsTransfers.transfers.Transfer;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

/**
 * @author sh1chiro 22.02.2023
 */
@Controller
public class CardDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public CardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Card> getClientsCards(int client_id){
        return jdbcTemplate.query("SELECT * FROM Card WHERE client_id=? AND status = 'active'", new Object[]{client_id},
                new BeanPropertyRowMapper<>(Card.class));
    }
    public Card showCard(String number){
        return jdbcTemplate.query("SELECT * FROM Card WHERE number=?", new Object[]{number},
                new BeanPropertyRowMapper<>(Card.class)).stream().findAny().orElse(null);
    }
    public void createCard(FreeCards freeCards, int id){
        jdbcTemplate.update("INSERT INTO Card VALUES(?,?,?,?)", id, freeCards.getNumber(),
                freeCards.getDate(), freeCards.getCvv());
    }
    public void closeCard(String number){
        jdbcTemplate.update("DELETE FROM Card WHERE number=?", new Object[]{number});
    }
    public boolean transferMoney(Transfer transfer, int id){
        int card_pay = 0;
        double cardMoney_pay = 0;
        String cardNumber_pay = "";
        List<Card> list = getClientsCards(id);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getNumber().equals(transfer.getPayCard())) {
                card_pay++;
                cardMoney_pay = list.get(i).getMoney();
                cardNumber_pay = list.get(i).getNumber();
            }
        }
        Card cardToPay = showCard(transfer.getCardToPay());
        if(card_pay > 0 && cardMoney_pay>=transfer.getMoney() && cardToPay != null){
            jdbcTemplate.update("UPDATE card SET money=? WHERE number=?", cardMoney_pay-transfer.getMoney(),
                    cardNumber_pay);
            jdbcTemplate.update("UPDATE card SET money=? WHERE number=?", cardToPay.getMoney()+transfer.getMoney(),
                    cardToPay.getNumber());
            return true;
        }
        else
            return false;
    }
    public boolean payment(Payment payment, int id){
        int card_pay = 0;
        double cardMoney_pay = 0;
        String cardNumber_pay = "";
        List<Card> list = getClientsCards(id);
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getNumber().equals(payment.getPayCard())) {
                card_pay++;
                cardMoney_pay = list.get(i).getMoney();
                cardNumber_pay = list.get(i).getNumber();
            }
        }
        if(card_pay>0 && cardMoney_pay>=payment.getMoney()){
            jdbcTemplate.update("UPDATE card SET money=? WHERE number=?", cardMoney_pay-payment.getMoney(),
                    cardNumber_pay);
            // + оплата на реквізити payment.getType().getNumber()
            return true;
        }else
            return false;
    }
}
