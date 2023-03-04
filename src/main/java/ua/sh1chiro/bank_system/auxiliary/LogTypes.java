package ua.sh1chiro.bank_system.auxiliary;
/**
 * @author sh1chiro 01.03.2023
 */
public enum LogTypes {
    AUTHORIZED("авторизовано", "authorizationLog/"),

    CREATE_CARD_ORDER("подано заявку на відкриття картки", "cardLog/"),
    CREATE_CARD_ORDER_APPROVE("заявку на відкриття картки схвалено", "cardLog/"),
    CREATE_CARD_ORDER_REFUSE("заявку на відкриття картки відхилено", "cardLog/"),

    CLOSE_CARD_ORDER("подано заявку на закриття картки", "cardLog/"),
    CLOSE_CARD_ORDER_APPROVE("заявку на закриття картки схвалено", "cardLog/"),
    CLOSE_CARD_ORDER_REFUSE("заявку на закриття картки відхилено", "cardLog/"),

    CREATE_CREDIT_ORDER("подано заявку на отримання кредиту", "creditLog/"),
    CREATE_CREDIT_ORDER_APPROVE("заявку на відкриття кредиту схвалено", "creditLog/"),
    CREATE_CREDIT_ORDER_REFUSE("заявку на відкриття кредиту відхилено", "creditLog/"),

    CREATE_DEPOSIT_ORDER("подано заявку на відкриття депозиту", "depositLog/"),
    CREATE_DEPOSIT_ORDER_APPROVE("заявку на відкриття депозиту схвалено", "depositLog/"),
    CREATE_DEPOSIT_ORDER_REFUSE("заявку на відкриття депозиту відхилено", "depositLog/"),

    VERIFICATION("подано заявку на верифікацію", "authorizationLog/"),
    VERIFICATION_APPROVE("заявку на верифікацію схвалено", "authorizationLog/"),
    VERIFICATION_REFUSE("заявку на верифікацію відхилено", "authorizationLog/"),

    PAYMENTS("value", "cardLog/"),
    TRANSFERS("value", "cardLog/");

    private String value;
    private String path;

    LogTypes(String value, String path) {
        this.value = value;
        this.path = path;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value){
        this.value=value;
    }
    public String getPath() {
        return path;
    }
}
