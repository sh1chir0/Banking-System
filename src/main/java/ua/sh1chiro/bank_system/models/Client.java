package ua.sh1chiro.bank_system.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sh1chiro 19.02.2023
 */
@Getter
@Setter
public class Client {
    @Min(value=10, message = "Код повинен містити 10 цифр")
    private int id;
    @NotEmpty(message = "Поле не може бути пустим")
    @Size(min = 2, max=30, message = "Введіть корректні дані")
    private String name;

    @Min(value = 1, message = "Вік повинен бути більше 0")
    private int age;
    private String phone;
    private boolean verified;
    private int client_id;
    public Client(){}
    public Client(int id, String name, int age, String phone, String password, boolean verified, int client_id) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.verified = verified;
        this.client_id = client_id;
    }
}