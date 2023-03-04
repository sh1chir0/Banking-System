package ua.sh1chiro.bank_system.controllers.clients;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.dao.deposits.DepositsDAO;
import ua.sh1chiro.bank_system.dao.deposits.DepositOrdersDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.deposits.DepositOrder;

/**
 * @author sh1chiro 25.02.2023
 */
@Controller
@RequestMapping("/bank/deposits")
public class DepositController {
    private final DepositOrdersDAO depositOrdersDAO;
    private final ClientDAO clientDAO;
    private final DepositsDAO depositsDAO;
    @Autowired
    public DepositController(DepositOrdersDAO depositOrdersDAO, ClientDAO clientDAO, DepositsDAO depositsDAO) {
        this.depositOrdersDAO = depositOrdersDAO;
        this.clientDAO = clientDAO;
        this.depositsDAO = depositsDAO;
    }
    @GetMapping
    public String depositingPage(){return Authorization.redirectClient("/bank/depositing/depositingPage");}
    @GetMapping("/my")
    public String myDeposits(Model model){
        try{
            int client_id = clientDAO.showClient(Authorization.getAuthorizedUser().getPhone()).getId();
            model.addAttribute("deposits", depositsDAO.showDeposits(client_id));
            return Authorization.redirectClient("/bank/depositing/my/my");
        }catch (NullPointerException ex){
            return ("redirect:/error/verification");
        }
    }
    @GetMapping("/my/{id}")
    public String myDeposit(@PathVariable("id") int id, Model model){
        try{
            model.addAttribute("deposit", depositsDAO.showDeposit(id));
            return Authorization.redirectClient("/bank/depositing/my/showMyDeposit");
        }catch(NullPointerException ex){
            return ("redirect:/error/verification");
        }

    }
    @GetMapping("/new")
    public String newDeposit(@ModelAttribute("deposit") DepositOrder depositOrder){
        return Authorization.redirectClient("/bank/depositing/creating/new");
    }
    @PostMapping("/new")
    public String createDepositOrder(@ModelAttribute("deposit") @Valid DepositOrder depositOrder, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/bank/depositing/creating/new";
        try{
            int id = Authorization.getAuthorizedClient().getId();
            depositOrdersDAO.createOrder(id, depositOrder);

            AccountLogging.writeLog(String.valueOf(id), LogTypes.CREATE_DEPOSIT_ORDER);
            return "/bank/depositing/creating/createWaiting";
        }
        catch(NullPointerException ex){
            return ("redirect:/error/verification");
        }
    }
}