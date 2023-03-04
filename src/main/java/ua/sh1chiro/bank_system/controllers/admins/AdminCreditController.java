package ua.sh1chiro.bank_system.controllers.admins;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.cards.FreeCardsDAO;
import ua.sh1chiro.bank_system.dao.credits.CreditOrdersDAO;
import ua.sh1chiro.bank_system.dao.credits.CreditsDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.credits.CreditOrders;
import ua.sh1chiro.bank_system.models.cards.FreeCards;

/**
 * @author sh1chiro 24.02.2023
 */
@Controller
@RequestMapping("/bank/admin/credit")
public class AdminCreditController {
    private final CreditsDAO creditsDAO;
    private final CreditOrdersDAO creditOrdersDAO;
    private final FreeCardsDAO freeCardsDAO;
    @Autowired
    public AdminCreditController(CreditOrdersDAO creditOrdersDAO, CreditsDAO creditsDAO, FreeCardsDAO freeCardsDAO) {
        this.creditOrdersDAO = creditOrdersDAO;
        this.creditsDAO = creditsDAO;
        this.freeCardsDAO = freeCardsDAO;
    }
    @GetMapping("/create-orders")
    public String orders(Model model){
        model.addAttribute("orders", creditOrdersDAO.showOrders());
        return Authorization.redirectAdmin("/bank/admin/lending/creditOrders");
    }
    @GetMapping("/create-orders/{id}")
    public String showOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", creditOrdersDAO.showOrder(id));
        return Authorization.redirectAdmin("/bank/admin/lending/showCreditOrder");
    }
    @PostMapping("/create-orders/{id}")
    public String approveCredit(@PathVariable("id") int id){
        CreditOrders creditOrders = creditOrdersDAO.showOrder(id);
        FreeCards freeCard = freeCardsDAO.getFreeCards();
        creditsDAO.createCredit(creditOrders.getId(),freeCard, creditOrders.getMoney());
        creditOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(creditOrders.getId()), LogTypes.CREATE_CREDIT_ORDER_APPROVE);
        return ("redirect:/bank/admin/credit/create-orders");
    }
    @DeleteMapping("/create-orders/{id}")
    public String refuseCredit(@PathVariable("id") int id){
        int client_id = creditOrdersDAO.showOrder(id).getId();
        creditOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.CREATE_CREDIT_ORDER_REFUSE);
        return ("redirect:/bank/admin/credit/create-orders");
    }
}
