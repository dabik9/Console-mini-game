package game.dao;

import game.config.DataBase;
import game.exceptions.GlobalExceptionHandler;
import game.model.Dish;
import game.model.Employee;
import game.model.Package;
import game.model.Product;
import game.model.User;
import game.model.enums.CategoryProduct;
import game.model.enums.Role;
import game.model.enums.Cagetory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    public User findByUsername (String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DataBase.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDate("brithdate").toLocalDate(),
                        Enum.valueOf(Role.class, rs.getString("role")),
                        rs.getInt("balance")
                );
            }

            else {
                return null;
            }

        } catch (SQLException e) {
            GlobalExceptionHandler.handle(99, e);
            return null;
        }
    }

    public List<Dish> allDish () {
        String sql = "SELECT * FROM dish";

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();
            List<Dish> dishes = new ArrayList<>();

            while (rs.next()) {
                dishes.add(new Dish(rs.getInt("id"), rs.getString("name"), Enum.valueOf(Cagetory.class, rs.getString("category")), rs.getInt("price")));
            }

            return dishes;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(100, e);
            return null;
        }
    }

    public void updatePriceOrder (User user, int price) {
        String sql = "UPDATE orders SET total_price = ? WHERE user_id = ?";

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, user.getId());

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Ошибка. Не получилось изменить цену заказа!");
            }
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(101, e);
        }
    }

    public List<Dish> allListDishOrder (int id) {
        String sql = """
                SELECT dish.id, dish.name, dish.category, dish.price
                FROM order_items
                JOIN dish ON dish.id = order_items.dish_id
                WHERE order_items.order_id = ?;
                """;

        List<Dish> dishes = new ArrayList<>();

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Dish dish = new Dish(
                        rs.getInt("id"),
                        rs.getString("name"),
                        Enum.valueOf(Cagetory.class, rs.getString("category")),
                        rs.getInt("price")
                );

                dishes.add(dish);
            }

            return dishes;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(102, e);
            return null;
        }
    }

    public boolean addDishOrder (int orderId, int dishId, int quantity, int price) {
        String sql = "INSERT INTO order_items (order_id, dish_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, price);

            return preparedStatement.executeUpdate() > 0;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(103, e);
            return false;
        }
    }

    public int numberOrderUser (User user) {
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            return -1;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(104, e);
            return -1;
        }
    }

    public boolean removeDishOrder (int dish_id, int order_id) {
        String sql = "DELETE FROM order_items WHERE dish_id = ? AND order_id = ?";

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, dish_id);
            preparedStatement.setInt(2, order_id);

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Не удалось удалить блюдо из меню!");
                return false;
            }

            return true;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(105, e);
            return false;
        }
    }

    public boolean setNewBalanceUser (User user, int balance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, balance);
            preparedStatement.setInt(2, user.getId());

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Ошибка. Не получилось изменить баланс пользователя!");
                return false;
            }

            return true;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(106, e);
            return false;
        }
    }

    public String getNumberOrder (User user) {
        String sql = "SELECT uuid FROM orders WHERE user_id = ?";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString("uuid");
            }

            return null;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(107, e);
            return null;
        }
    }

    public List<Employee> allEmployee () {
        String sql = "SELECT * FROM employee";

        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        Enum.valueOf(Role.class, rs.getString("role")),
                        rs.getInt("salary"),
                        rs.getInt("bonus")
                );

                employeeList.add(employee);
            }

            return employeeList;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(108, e);
            return null;
        }
    }

    public boolean checkFakeNumberOrder (String uuid) {
        String sql = "SELECT uuid FROM orders WHERE uuid = ?";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, uuid);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return true;
            }

            return false;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(109, e);
            return false;
        }
    }

    public List<User> allUsers () {
        String sql = "SELECT * FROM users";

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();

            while (rs.next()) {
                if (!(rs.getString("username").equalsIgnoreCase("Danil")) || !(rs.getString("username").equalsIgnoreCase("test"))) {
                    User user = new User (
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getDate("brithdate").toLocalDate(),
                            Enum.valueOf(Role.class, rs.getString("role")),
                            rs.getInt("balance")
                    );

                    userList.add(user);
                }
            }

            return userList;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(110, e);
            return null;
        }
    }

    /*
    * Локация склад wb
    * */

    public List<Product> allProduct (User user) {
        String sql = """
                SELECT package.*, product.name, product.category, product.price
                FROM package
                JOIN product ON package.product_id = product.id
                WHERE package.user_id = ?
                """;

        List<Product> productList = new ArrayList<>();

        try (Connection connection = DataBase.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        CategoryProduct.valueOf(CategoryProduct.class, rs.getString("category")),
                        rs.getInt("price")
                );

                productList.add(product);
            }

            return productList;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(111, e);
            return null;
        }
    }

    public List<Product> allProductInStore () {
        String sql = "SELECT * FROM product";

        List<Product> productList = new ArrayList<>();

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        CategoryProduct.valueOf(CategoryProduct.class, rs.getString("category")),
                        rs.getInt("price")
                );

                productList.add(product);
            }

            return productList;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(112, e);
            return null;
        }
    }

    public void addProduct (User user, Product product, String uuid) {
        String sql = "INSERT INTO package (addressee, user_id, uuid, price, product_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.setString(3, uuid);
            preparedStatement.setInt(4, product.getPrice());
            preparedStatement.setInt(5, product.getId());

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Не удалось добавить товар в посылку!");
            }
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(113, e);
        }
    }

    public boolean removeProduct (User user) {
        String sql = "DELETE FROM package WHERE user_id = ?";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Не удалось выдать посылку!");
                return false;
            }

            return true;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(114, e);
            return false;
        }
    }

    public void updatePriceProduct (User user, int newPrice) {
        String sql = "UPDATE package SET price = ? WHERE user_id = ?";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newPrice);
            preparedStatement.setInt(2, user.getId());

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Не удалось обновить цену посылки!");
            }
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(115, e);
        }
    }

    public List<Package> allPackage () {
        String sql = "SELECT * FROM package";

        List<Package> packageList = new ArrayList<>();

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Package packageLocal = new Package (
                        rs.getInt("id"),
                        rs.getString("addressee"),
                        rs.getInt("user_id"),
                        rs.getString("uuid"),
                        rs.getInt("price")
                );

                packageList.add(packageLocal);
            }

            return packageList;
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(116, e);
            return null;
        }
    }

    public void setNewRoleUser (Role roleUser, User user) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";

        try (Connection connection = DataBase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, roleUser.toString());
            preparedStatement.setInt(2, user.getId());

            int rows = preparedStatement.executeUpdate();

            if (!(rows > 0)) {
                System.out.println("[System]: Не удалось сменить на роль: " + roleUser);
            }
        }

        catch (SQLException e) {
            GlobalExceptionHandler.handle(117, e);
        }
    }
}