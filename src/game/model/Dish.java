package game.model;

public class Dish {
    private int id;
    private String name;
    private Enum Category;
    private int price;

    public Dish (int id, String name, Enum Category, int price) {
        this.id = id;
        this.name = name;
        this.Category = Category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Enum getCategory() {
        return Category;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Enum category) {
        Category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Информация о блюде: " + "\n" +
                "Id: " + id + "\n" +
                "Название: " + name + "\n" +
                "Категория: " + Category + "\n" +
                "Цена: " + price + " рублей.";
    }
}