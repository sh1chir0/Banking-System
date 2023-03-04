package ua.sh1chiro.bank_system.controllers.clients;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.Iban;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.dao.cards.CardDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.paymentsTransfers.payments.Payment;
import ua.sh1chiro.bank_system.models.paymentsTransfers.transfers.Transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sh1chiro 25.02.2023
 */
@Controller
@RequestMapping("/bank/payments-transfers")
public class PaymentsTransfersController {
    private final CardDAO cardDAO;
    private final ClientDAO clientDAO;
    @Autowired
    public PaymentsTransfersController(CardDAO cardDAO, ClientDAO clientDAO) {
        this.cardDAO = cardDAO;
        this.clientDAO = clientDAO;
    }
    @GetMapping
    public String paymentsTransfersPage(){return Authorization.redirectClient("/bank/paymentsTransfers/paymentsTransfersPage");}
    @GetMapping("/transfer")
    public String transferForm(@ModelAttribute("transfer") Transfer transfer, Model model){
        try{
            model.addAttribute("cards", cardDAO.getClientsCards(Authorization.getAuthorizedClient().getId()));
            return Authorization.redirectClient("/bank/paymentsTransfers/transfers/transfers");
        }catch(NullPointerException ex){
            return("redirect:/error/verification");
        }
    }
    @PostMapping("/transfer")
    public String transfer(@ModelAttribute("transfer") @Valid Transfer transfer, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/bank/paymentsTransfers/transfers/transfers";
        if(cardDAO.transferMoney(transfer, clientDAO.showClient(Authorization.getAuthorizedUser().getPhone()).getId())){
            AccountLogging.writeTransferLog(String.valueOf(Authorization.getAuthorizedClient().getId()), LogTypes.TRANSFERS,
                    transfer);
            return "/bank/paymentsTransfers/transfers/transferSuccessfully";
        }
        else
            return "/bank/paymentsTransfers/transfers/transferUnsuccessfully";
    }

    @GetMapping("/payments")
    public String paymentsForm(@ModelAttribute("payment") Payment payment, Model model){
        List<Iban> list = new ArrayList<>();
        list.add(Iban.EDUCATION);
        list.add(Iban.INTERNET);
        list.add(Iban.UTILITY);
        list.add(Iban.FINES);
        list.add(Iban.TAXES);
        model.addAttribute("list", list);
        return Authorization.redirectClient("/bank/paymentsTransfers/payments/payments");
    }
    @PostMapping("/payments")
    public String pay(@ModelAttribute("payment") @Valid Payment payment, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return ("redirect:/bank/payments-transfers/payments");
        try{
            if(cardDAO.payment(payment, clientDAO.showClient(Authorization.getAuthorizedUser().getPhone()).getId())){
                AccountLogging.writePaymentLog(String.valueOf(Authorization.getAuthorizedClient().getId()), LogTypes.PAYMENTS,
                        payment);
                return "/bank/paymentsTransfers/payments/paymentSuccessfully";
            }
            else
                return "/bank/paymentsTransfers/payments/paymentUnsuccessfully";
        }catch(NullPointerException ex){
            return ("redirect:/error/verification");
        }
    }
}