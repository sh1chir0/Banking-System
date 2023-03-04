package ua.sh1chiro.bank_system.auxiliary;

import ua.sh1chiro.bank_system.dao.deposits.DepositsDAO;
import java.util.*;

/**
 * @author sh1chiro 27.02.2023
 */
public class DepositAlarm {
    private Timer timer;
    private DepositsDAO depositsDAO;
    public DepositAlarm(DepositsDAO depositsDAO) {
        this.depositsDAO = depositsDAO;
    }
    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                depositsDAO.calculatePercents();
            }
        }, 0, 216000000);
    }
}