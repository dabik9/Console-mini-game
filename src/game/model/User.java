package game.model;

import java.time.LocalDate;

public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private Enum Role;
    private int balance;

    public User (int id, String name, String username, String password, String email, String phone, LocalDate birthDate, Enum role, int balance) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.Role = role;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Enum getRole() {
        return Role;
    }

    public int getBalance() {
        return balance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setRole (Enum role) {
        Role = role;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String toString () {
        return "Информация о пользователе: " + "\n" +
                "id: " + id + "\n" +
                "Имя: " + name + "\n" +
                "Логин: " + username + "\n" +
                "Пароль: " + password + "\n" +
                "Email: " + email + "\n" +
                "Телефон: " + phone + "\n" +
                "Дата рождения: " + birthDate + "\n" +
                "Роль: " + Role + "\n" +
                "Баланс: " + balance + "\n";
    }
}