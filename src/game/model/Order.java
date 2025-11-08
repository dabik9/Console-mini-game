package game.model;

public interface Order {
    void addProduct();
    void removeProduct();
    int resultPrice(User user);
}