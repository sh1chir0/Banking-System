package ua.sh1chiro.bank_system.controllers.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.sh1chiro.bank_system.auxiliary.Authorization;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;

/**
 * @author sh1chiro 24.02.2023
 */
@Controller
@RequestMapping("/bank/admin")
public class AdminVerificationController {
    private final ClientDAO clientDAO;
    @Autowired
    public AdminVerificationController(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
    @GetMapping("/check-verification")
    public String checkVerification(Model model){
        model.addAttribute("orders", clientDAO.showUnverified());
        return Authorization.redirectAdmin("/bank/admin/verification/checkVerification");
    }
    @GetMapping("/check-verification/{id}")
    public String checkVerificationOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", clientDAO.showClient(id));
        return Authorization.redirectAdmin("/bank/admin/verification/checkVerificationOrder");
    }
    @PostMapping("/check-verification/{id}")
    public String approveVerification(@PathVariable("id") int id){
        clientDAO.approveVerification(id);

        AccountLogging.writeLog(String.valueOf(clientDAO.showClient(id).getId()), LogTypes.VERIFICATION_APPROVE);
        return ("redirect:/bank/admin/check-verification");
    }
    @DeleteMapping("/check-verification/{id}")
    public String refuseVerification(@PathVariable("id") int id){
        int client_id = clientDAO.showClient(id).getId();
        clientDAO.refuseVerification(id);

        AccountLogging.writeLog(String.valueOf(client_id), LogTypes.VERIFICATION_REFUSE);
        return ("redirect:/bank/admin/check-verification");
    }
}
