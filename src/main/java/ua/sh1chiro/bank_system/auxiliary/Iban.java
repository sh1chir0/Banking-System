package ua.sh1chiro.bank_system.auxiliary;

/**
 * @author sh1chiro 25.02.2023
 */
public enum Iban {
    UTILITY(1000000001, "Комунальні платежі"),
    INTERNET(1000000002,"Інтернет"),
    TAXES(1000000003, "Податки"),
    EDUCATION(1000000004, "Навчання"),
    FINES(1000000005, "Штрафи");
    private int number;
    private String name;
    Iban(int number, String name) {
        this.number = number;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getNumber() {
        return number;
    }
}