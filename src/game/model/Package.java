package game.model;

public class Package {
    private int id;
    private String addressee;
    private int userId;
    private String uuid;
    private int price;

    public Package (int id, String addressee, int userId, String uuid, int price) {
        this.id = id;
        this.addressee = addressee;
        this.userId = userId;
        this.uuid = uuid;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getAddressee() {
        return addressee;
    }

    public int getUserId() {
        return userId;
    }

    public String getUuid() {
        return uuid;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString () {
        return "Информация о посылке: \n" +
                "Id: " + id + "\n" +
                "Получатель: " + addressee + "\n" +
                "Уникальный код: " + uuid + "\n" +
                "Цена: " + price + " рублей";
    }
}