package ua.sh1chiro.bank_system.logging;
import ua.sh1chiro.bank_system.auxiliary.LogTypes;
import ua.sh1chiro.bank_system.models.paymentsTransfers.payments.Payment;
import ua.sh1chiro.bank_system.models.paymentsTransfers.transfers.Transfer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author sh1chiro 28.02.2023
 */
public class AccountLogging{
    /**
     * Записує в необхідний файл логи.
     * @param fileName - ім'я файлу, в який логується.
     * @param logtypes - об'єкт enum, який визначає тип логування.
     */
    public static void writeLog(String fileName, LogTypes logtypes){
        LocalDateTime localDateTime = LocalDateTime.now();
        try {
            FileWriter writer = new FileWriter(createFilePath(fileName, logtypes), true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            String date = localDateTime.getYear() + "." + localDateTime.getDayOfMonth() + "." + localDateTime.getMonth()
                    + " " + localDateTime.getHour() + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond() + " | ";
            bufferWriter.write(date + logtypes.getValue() + "\n");
            bufferWriter.close();
        }
        catch (IOException e) {}
    }

    /**
     * Спеціальний метод для логування платежів.
     * @param fileName - ім'я файлу, в який логується.
     * @param logTypes - об'єкт enum, який визначає тип логування.
     * @param payment - об'єкт Payment, який містить в собі всі дані платежа, що логується.
     */
    public static void writePaymentLog(String fileName, LogTypes logTypes, Payment payment){
        logTypes.setValue("з картки " + payment.getPayCard() + " сплачено за " + payment.getType().getName() + " "
                + payment.getMoney() + " грн.");
        writeLog(fileName, logTypes);
    }
    /**
     * Спеціальний метод для логування переказів.
     * @param fileName - ім'я файлу, в який логується.
     * @param logTypes - об'єкт enum, який визначає тип логування.
     * @param transfer - об'єкт Transfer, який містить в собі всі дані переказу, що логується.
     */
    public static void writeTransferLog(String fileName, LogTypes logTypes, Transfer transfer){
        logTypes.setValue("з картки " + transfer.getPayCard() + " переведено " + transfer.getMoney() + " грн. на картку "
                + transfer.getCardToPay());
        writeLog(fileName, logTypes);
    }
    public void readLog(){

    }

    /**
     * Файл, що створює шлях до файлу.
     * @param fileName - ім'я файлу, в який логується.
     * @param logTypes - об'єкт enum, який визначає тип логування.
     * @return Шлях до файлу.
     */
    private static String createFilePath(String fileName, LogTypes logTypes){
        String path = "D:/Documents/Programming/java/Projects/Sh1chiros Bank/src/main/resources/logging/";
        String filePath = path + logTypes.getPath() + fileName + ".txt";
        return filePath;
    }
}