package game.model;

public class Orders {
    private int id;
    private String uuid;
    private int userId;
    private int totalPrice;

    public Orders (String uuid, int userId, int totalPrice) {
        this.uuid = uuid;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public int getUserId() {
        return userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}