package game.model;

public class Animal {
    private int id;
    private String name;
    private int age;
    private Enum categoryAnimal;
    private int food;

    public Animal (int id, String name, int age, Enum categoryAnimal, int food) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.categoryAnimal = categoryAnimal;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Enum getCategoryAnimal() {
        return categoryAnimal;
    }

    public int getFood() {
        return food;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCategoryAnimal(Enum categoryAnimal) {
        this.categoryAnimal = categoryAnimal;
    }

    public void setFood(int food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "Информация о животном: \n" +
                "Id: " + id + "\n" +
                "Имя: " + name + "\n" +
                "Возраст: " + age + "\n" +
                "Категория: " + categoryAnimal + "\n" +
                "Количество еды (в день): " + food + " кг";
    }
}