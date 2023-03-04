package ua.sh1chiro.bank_system.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 24.02.2023
 */
@Getter
@Setter
public class User {
    @NotEmpty(message = "Телефон не може бути порожнім")
    private String phone;
    @NotEmpty(message = "Пароль не може бути порожнім")
    private String password;
    private String type;
    private int id;

    public User(String phone, String password, String type, int id) {
        this.phone = phone;
        this.password = password;
        this.type = type;
        this.id = id;
    }

    public User(){
    }
}
