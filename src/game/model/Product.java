package game.model;

public class Product {
    private int id;
    private String name;
    private Enum CategoryProduct;
    private int price;

    public Product (int id, String name, Enum CategoryProduct, int price) {
        this.id = id;
        this.name = name;
        this.CategoryProduct = CategoryProduct;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Enum getCategoryProduct() {
        return CategoryProduct;
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

    public void setCategoryProduct(Enum categoryProduct) {
        CategoryProduct = categoryProduct;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Информация о товаре: \n" +
                "Id: " + id + "\n" +
                "Название товара: " + name + "\n" +
                "Категория: " + CategoryProduct + "\n" +
                "Цена: " + price + " рублей.";
    }
}