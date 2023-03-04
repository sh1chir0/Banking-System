package ua.sh1chiro.bank_system.auxiliary;

/**
 * @author sh1chiro 28.02.2023
 */
public enum UserType {
    CLIENT("client"),ADMIN("admin");
    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
