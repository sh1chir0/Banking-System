package ua.sh1chiro.bank_system.controllers;
/**
 *@author sh1chiro 18.02.2023
 */
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.deposits.DepositsDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.dao.UsersDAO;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.models.Client;
import ua.sh1chiro.bank_system.models.User;

@Controller
@RequestMapping("/bank")
public class BankController {
    private final ClientDAO clientDAO;
    private final UsersDAO usersDAO;
    private DepositsDAO depositsDAO;
    private Authorization authorization;

    public BankController(ClientDAO clientDAO, UsersDAO usersDAO, DepositsDAO depositsDAO) {
        this.clientDAO = clientDAO;
        this.authorization = new Authorization();
        this.usersDAO = usersDAO;
        this.depositsDAO = depositsDAO;
    }

    @GetMapping("/authorization")
    public String authorization(@ModelAttribute("user") User user) {
        return "/bank/authorization";
    }

    @PostMapping("/authorization")
    public String checkAuthorization(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/bank/authorization";
        authorization = new Authorization(user, usersDAO, clientDAO, depositsDAO);

        return authorization.authorization();
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "/bank/registration";
    }

    @PostMapping("/registration")
    public String newClient(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/bank/registration";
        usersDAO.newUser(user);
        return ("redirect:/bank");
    }

    @GetMapping
    public String homePage() {
        if (Authorization.getAuthorizedUser() != null) {
            if (usersDAO.getUserType().equals("admin"))
                return "/bank/admin/adminPage";
            else
                return "/bank/homePage";
        } else
            return ("redirect:/bank/authorization");
    }

    //for client
    @GetMapping("/verification/pass-verification")
    public String passVerification() {
        return "/bank/verification/passVerification";
    }

    @GetMapping("/verification")
    public String verification(@ModelAttribute("client") Client client) {
        return Authorization.redirectClient("/bank/verification/verification");
    }

    @PostMapping("/verification")
    public String newClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/bank/verification/verification";
        try {
            client.setPhone(Authorization.getAuthorizedUser().getPhone());
            clientDAO.newClient(client);

            AccountLogging.writeLog(String.valueOf(client.getId()), LogTypes.VERIFICATION);
            return ("redirect:/bank");
        }catch(DataIntegrityViolationException ex){
            return ("redirect:/error/verification-true");
        }

    }
}