package ua.sh1chiro.bank_system.auxiliary;

import ua.sh1chiro.bank_system.dao.ClientDAO;
import ua.sh1chiro.bank_system.dao.UsersDAO;
import ua.sh1chiro.bank_system.dao.deposits.DepositsDAO;
import ua.sh1chiro.bank_system.logging.AccountLogging;
import ua.sh1chiro.bank_system.models.Client;
import ua.sh1chiro.bank_system.models.User;

/**
 * @author sh1chiro 20.02.2023
 */
public class Authorization {
    private User user;
    private static User authorizedUser;
    private static boolean authorized;
    private static UsersDAO usersDAO;
    private static ClientDAO clientDAO;
    private static DepositsDAO depositsDAO;
    public Authorization(){}
    public Authorization(User user, UsersDAO usersDAO, ClientDAO clientDAO, DepositsDAO depositsDAO) {
        this.user = user;
        this.usersDAO = usersDAO;
        this.clientDAO = clientDAO;
        this.depositsDAO = depositsDAO;
    }

    /**
     * Метод авторизації.
     * При виклику встановлює авторизованого користувача, запускає систему депозиту,
     * якщо у даного юзера є депозит, та записує в файл логу авторизацію.
     */
    public String authorization(){
        authorizedUser = usersDAO.getUser(user.getPhone());
        if(authorizedUser != null && authorizedUser.getPassword().equals(user.getPassword())){
            authorized = true;

            if(depositsDAO.isDeposit()) {
                DepositAlarm depositAlarm = new DepositAlarm(depositsDAO);
                depositAlarm.startTimer();
            }

            AccountLogging.writeLog(authorizedUser.getPhone(), LogTypes.AUTHORIZED);
            return ("redirect:/bank");
        }
        else
            return "/bank/authorization";
    }

    /**
     * Метод, що перевіряє юзера на адміністратора, тим самим не дає доступ клієнтам до сторінок адміністратора.
     * @param link - посилання, на потрібну сторінку.
     * @return Якщо у юзера тип облікового запису - адміністратор, повертає посилання, якщо - клієнт, переадресовує на основну сторінку.
     */
    public static String redirectAdmin(String link){
        if(authorized && authorizedUser.getType().equals(UserType.ADMIN.getType())){
            return link;
        }
        else
            return ("redirect:/bank");
    }
    public static String redirectClient(String link){
        if(authorized && authorizedUser.getType().equals(UserType.CLIENT.getType())){
            return link;
        }
        else
            return ("redirect:/bank");
    }
    public static User getAuthorizedUser() {
        return authorizedUser;
    }
    public static Client getAuthorizedClient(){
        return clientDAO.showClient(authorizedUser.getPhone());
    }
}