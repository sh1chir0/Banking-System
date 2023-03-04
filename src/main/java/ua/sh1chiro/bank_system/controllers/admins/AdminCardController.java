package ua.sh1chiro.bank_system.controllers.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.*;
import ua.sh1chiro.bank_system.dao.cards.CardCloseOrdersDAO;
import ua.sh1chiro.bank_system.dao.cards.CardDAO;
import ua.sh1chiro.bank_system.dao.cards.CardOrdersDAO;
import ua.sh1chiro.bank_system.dao.cards.FreeCardsDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.cards.FreeCards;

/**
 * @author sh1chiro 24.02.2023
 */
@Controller
@RequestMapping("/bank/admin")
public class AdminCardController{
    private final CardOrdersDAO cardOrdersDAO;
    private final CardDAO cardDAO;
    private final FreeCardsDAO freeCardsDAO;
    private final CardCloseOrdersDAO cardCloseOrdersDAO;
    private final ClientDAO clientDAO;
    @Autowired
    public AdminCardController(CardOrdersDAO cardOrdersDAO, FreeCardsDAO freeCardsDAO, CardDAO cardDAO,
                           CardCloseOrdersDAO cardCloseOrdersDAO, ClientDAO clientDAO) {
        this.cardOrdersDAO = cardOrdersDAO;
        this.freeCardsDAO = freeCardsDAO;
        this.cardDAO = cardDAO;
        this.cardCloseOrdersDAO = cardCloseOrdersDAO;
        this.clientDAO = clientDAO;
    }
    @GetMapping("/card-orders")
    public String orders(Model model){
        model.addAttribute("orders", cardOrdersDAO.showOrders());
        return Authorization.redirectAdmin("/bank/admin/cardCreate/cardOrders");
    }
    @GetMapping("/card-order/{id}")
    public String showOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", cardOrdersDAO.showOrder(id));
        return Authorization.redirectAdmin("/bank/admin/cardCreate/showCardOrder");
    }
    @PostMapping("/card-order/{id}")
    public String approveCard(@PathVariable("id") int id){
        FreeCards freeCard = freeCardsDAO.getFreeCards();
        int client_id = cardOrdersDAO.showOrder(id).getId();
        cardDAO.createCard(freeCard, client_id);
        freeCardsDAO.changeStatus(freeCard);
        cardOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.CREATE_CARD_ORDER_APPROVE);
        return ("redirect:/bank/admin/card-orders");
    }
    @DeleteMapping("/card-order/{id}")
    public String refuseCard(@PathVariable("id") int id){
        int client_id = cardOrdersDAO.showOrder(id).getId();
        cardOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.CREATE_CARD_ORDER_REFUSE);
        return ("redirect:/bank/admin/card-orders");
    }
    @GetMapping("/card-close-orders")
    public String cardCloseOrders(Model model){
        model.addAttribute("closeOrders", cardCloseOrdersDAO.showOrders());
        return Authorization.redirectAdmin("/bank/admin/cardClose/cardCloseOrders");
    }
    @GetMapping("/card-close-orders/{id}")
    public String cardCloseOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("closeOrder", cardCloseOrdersDAO.showOrder(id));
        return Authorization.redirectAdmin("/bank/admin/cardClose/showCardCloseOrders");
    }
    @PostMapping("/card-close-orders/{id}")
    public String closeCard(@PathVariable("id") int id){
        int client_id = cardCloseOrdersDAO.showOrder(id).getId();
        String number = cardCloseOrdersDAO.showOrder(id).getNumber();
        cardCloseOrdersDAO.deleteOrder(id);
        cardDAO.closeCard(number);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.CLOSE_CARD_ORDER_APPROVE);
        return ("redirect:/bank/admin/card-close-orders");
    }
    @DeleteMapping("/card-close-orders/{id}")
    public String refuseOrder(@PathVariable("id") int id){
        int client_id = cardCloseOrdersDAO.showOrder(id).getId();
        cardCloseOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.CLOSE_CARD_ORDER_REFUSE);
        return ("redirect:/bank/admin/card-close-orders");
    }
}
