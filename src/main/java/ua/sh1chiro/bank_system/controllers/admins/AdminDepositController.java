package ua.sh1chiro.bank_system.controllers.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.dao.cards.FreeCardsDAO;
import ua.sh1chiro.bank_system.dao.deposits.DepositsDAO;
import ua.sh1chiro.bank_system.dao.deposits.DepositOrdersDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.cards.FreeCards;
import ua.sh1chiro.bank_system.models.deposits.DepositOrder;

/**
 * @author sh1chiro 25.02.2023
 */
@Controller
@RequestMapping("/bank/admin/deposit")
public class AdminDepositController {
    private final DepositOrdersDAO depositOrdersDAO;
    private final ClientDAO clientDAO;
    private final DepositsDAO depositsDAO;
    private final FreeCardsDAO freeCardsDAO;
    @Autowired
    public AdminDepositController(DepositOrdersDAO depositOrdersDAO, ClientDAO clientDAO, DepositsDAO depositsDAO,
                                  FreeCardsDAO freeCardsDAO) {
        this.depositOrdersDAO = depositOrdersDAO;
        this.clientDAO = clientDAO;
        this.depositsDAO = depositsDAO;
        this.freeCardsDAO = freeCardsDAO;
    }
    @GetMapping("/create-orders")
    public String orders(Model model){
        model.addAttribute("orders", depositOrdersDAO.showOrders());
        return Authorization.redirectAdmin("/bank/admin/depositing/depositOrders");
    }
    @GetMapping("/create-orders/{id}")
    public String showOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", depositOrdersDAO.showOrder(id));
        return Authorization.redirectAdmin("/bank/admin/depositing/showDepositOrder");
    }
    @PostMapping("/create-orders/{id}")
    public String approveOrder(@PathVariable("id") int id){
        FreeCards freeCard = freeCardsDAO.getFreeCards();
        DepositOrder depositOrder = depositOrdersDAO.showOrder(id);
        depositsDAO.createDeposit(freeCard, depositOrder);
        depositOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(depositOrder.getId()), LogTypes.CREATE_DEPOSIT_ORDER_APPROVE);
        return ("redirect:/bank/admin/deposit/create-orders");
    }
    @DeleteMapping("/create-orders/{id}")
    public String refuseOrder(@PathVariable("id") int id){
        int client_id = depositOrdersDAO.showOrder(id).getId();
        depositOrdersDAO.deleteOrder(id);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.CREATE_DEPOSIT_ORDER_REFUSE);
        return ("redirect:/bank/admin/deposit/create-orders");
    }
}
