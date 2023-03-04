package ua.sh1chiro.bank_system.controllers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.cards.CardCloseOrdersDAO;
import ua.sh1chiro.bank_system.dao.cards.CardDAO;
import ua.sh1chiro.bank_system.dao.cards.CardOrdersDAO;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.cards.Card;


/**
 * @author sh1chiro 19.02.2023
 */
@Controller
@RequestMapping("/bank/card")
public class CardController {
    private final CardDAO cardDAO;
    private final CardOrdersDAO cardOrdersDAO;
    private final CardCloseOrdersDAO cardCloseOrdersDAO;
    private final ClientDAO clientDAO;
    @Autowired
    public CardController(ClientDAO clientDAO, CardDAO cardDAO, CardOrdersDAO cardOrdersDAO, CardCloseOrdersDAO cardCloseOrdersDAO) {
        this.clientDAO=clientDAO;
        this.cardDAO = cardDAO;
        this.cardOrdersDAO = cardOrdersDAO;
        this.cardCloseOrdersDAO = cardCloseOrdersDAO;
    }
    @GetMapping
    public String cardPage(){return Authorization.redirectClient("/bank/cardTransaction/cardPage");}
    @GetMapping("my")
    public String my(Model model){
        try{
            model.addAttribute("cards", cardDAO.getClientsCards(Authorization.getAuthorizedClient().getId()));
            return Authorization.redirectClient("/bank/cardTransaction/my/my");
        }catch(Exception ex){
            return ("redirect:/error/verification");
        }
    }
    @GetMapping("{number}")
    public String showCard(@PathVariable("number") String number, Model model){
        if(Authorization.getAuthorizedUser() != null)
            model.addAttribute("card", cardDAO.showCard(number));
        return Authorization.redirectClient("/bank/cardTransaction/my/show");
    }
    @DeleteMapping("/close/{number}")
    public String closeCard(@PathVariable("number") String number){
        Card card = cardDAO.showCard(number);
        cardCloseOrdersDAO.closeCard(number);

        AccountLogging.writeLog(String.valueOf(card.getClient_id()), LogTypes.CLOSE_CARD_ORDER);
        return ("redirect:/bank/card/my");
    }
    @GetMapping("/new")
    public String newCard(){
        return Authorization.redirectClient("/bank/cardTransaction/creating/new");
    }
    @PostMapping("/new")
    public String createCard(){
        if(clientDAO.verified()){
            cardOrdersDAO.orderCard(clientDAO.showClient(Authorization.getAuthorizedUser().getPhone()));

            AccountLogging.writeLog(String.valueOf(Authorization.getAuthorizedClient().getId()), LogTypes.CREATE_CARD_ORDER);
            return "/bank/cardTransaction/creating/createWaiting";
        }
        else{
            return ("redirect:/bank/verification/pass-verification");
        }
    }
}