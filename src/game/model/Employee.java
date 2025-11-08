package game.model;

public class Employee {
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Enum Role;
    private int salary;
    private int bonus;

    public Employee(int id, String name, String username, String password, String email, String phone, Enum Role, int salary, int bonus) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.Role = Role;
        this.salary = salary;
        this.bonus = bonus;
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

    public Enum getRole() {
        return Role;
    }

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
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

    public void setRole(Enum role) {
        Role = role;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Информация о сотруднике: " + "\n" +
                "Id: " + id + "\n" +
                "Имя: " + name + "\n" +
                "Логин: " + username + "\n" +
                "Email: " + email + "\n" +
                "Телефон: " + phone + "\n" +
                "Роль: " + Role + "\n" +
                "Зарплата: " + salary + " рублей." + "\n" +
                "Бонус: " + (bonus == 0 ? "Отсутствует!" : bonus + " рублей.");
    }
}