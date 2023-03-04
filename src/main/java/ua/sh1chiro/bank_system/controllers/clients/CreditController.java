package ua.sh1chiro.bank_system.controllers.clients;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.dao.credits.CreditOrdersDAO;
import ua.sh1chiro.bank_system.dao.credits.CreditsDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.credits.CreditOrders;

/**
 * @author sh1chiro 24.02.2023
 */
@Controller
@RequestMapping("/bank/credit")
public class CreditController {
    private final CreditOrdersDAO creditOrdersDAO;
    private final CreditsDAO creditsDAO;
    private final ClientDAO clientDAO;
    @Autowired
    public CreditController(CreditOrdersDAO creditOrdersDAO, ClientDAO clientDAO, CreditsDAO creditsDAO) {
        this.creditOrdersDAO = creditOrdersDAO;
        this.clientDAO = clientDAO;
        this.creditsDAO = creditsDAO;
    }
    @GetMapping
    public String lendingPage(){return Authorization.redirectClient("/bank/lending/lendingPage");}
    @GetMapping("/my")
    public String my(Model model){
        try{
            int id = clientDAO.showClient(Authorization.getAuthorizedUser().getPhone()).getId();
            model.addAttribute("credits", creditsDAO.showCredits(id));
            return Authorization.redirectClient("/bank/lending/my/my");
        }catch (NullPointerException ex){
            return ("redirect:/error/verification");
        }
    }
    @GetMapping("/my/{id}")
    public String showCredit(@PathVariable("id") int id, Model model){
        model.addAttribute("credit", creditsDAO.showCredit(id));
        return Authorization.redirectClient("/bank/lending/my/show");
    }
    @GetMapping("/new")
    public String newCredit(@ModelAttribute("credit") CreditOrders creditOrders){
        return Authorization.redirectClient("/bank/lending/creating/new");
    }
    @PostMapping("/new")
    public String createCreditOrder(@ModelAttribute("credit") @Valid CreditOrders creditOrders, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/bank/lending/creating/new";
        try{
            int id = Authorization.getAuthorizedClient().getId();
            creditOrdersDAO.createOrder(id, creditOrders);

            AccountLogging.writeLog(String.valueOf(id), LogTypes.CREATE_CREDIT_ORDER);
            return "/bank/lending/creating/createWaiting";
        }catch(NullPointerException ex){
            return ("redirect:/error/verification");
        }

    }
}
