package game.service;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.model.Employee;
import game.model.Order;
import game.model.Package;
import game.model.Product;
import game.model.User;

import java.util.*;

public class StoreOrder implements Order {
    private final DAO dao;
    private final Session session;
    private final Scanner CONSOLE = new Scanner(System.in);
    private int counter = 0;

    public StoreOrder (DAO dao, Session session) {
        this.dao = dao;
        this.session = session;
    }


    public void allProductInStore () {
        List<Product> productList = dao.allProductInStore();

        if (productList.isEmpty()) {
            System.out.println("[System]: В данный момент посмотреть каталог товаров нельзя. Попробуйте позже!");
            return;
        }

        System.out.println("[CONSOLE]: Каталог: ");
        try {
            for (Product product : productList) {
                Thread.sleep(500);
                System.out.println("Id: " + product.getId() + ", название: " + product.getName() + ", категория: " + product.getCategoryProduct() + ", цена: " + product.getPrice());
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }


    public void allProductUser () {
        List<Product> productList = dao.allProduct(session.getUser());

        if (productList.isEmpty()) {
            System.out.println("[System]: Вы ничего не заказали!");
            return;
        }

        System.out.println("[Паймон]: А вот и наши посылки)");
        System.out.println();

        try {
            for (Product product : productList) {
                System.out.println("Id: " + product.getId() + ", название: " + product.getName() + ", категория: " + product.getCategoryProduct() + ", цена: " + product.getPrice());
                Thread.sleep(500);
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }

        System.out.println();
    }

    @Override
    public void addProduct () {
        List<Product> productList = dao.allProductInStore();

        if (productList.isEmpty()) {
            System.out.println("[System]: В данный момент идут технические работы! Попробуйте позже!");
            return;
        }

        System.out.print("[Паймон]: Введи номер продукта, который хочешь добавить в заказ: ");

        try {
            int id = Integer.parseInt(CONSOLE.nextLine());
            String uuid = UUID.randomUUID().toString();

            for (Product product : productList) {
                if (id == product.getId()) {
                    dao.addProduct(session.getUser(), product, uuid);

                    int result = resultPrice(session.getUser());

                    if (dao.setNewBalanceUser(session.getUser(), result)) {
                        session.getUser().setBalance(result);
                    }

                    System.out.println("[System]: Товар с Id: " + id + " добавлен в посылку!");
                    return;
                }
            }

            System.out.println("[System]: Товар с таким ID:" + id + " не найден!");
        }

        catch (NumberFormatException e) {
            GlobalExceptionHandler.handle(20, e);
        }
    }

    @Override
    public void removeProduct () {
         List<Product> productList = dao.allProduct(session.getUser());
        List<Employee> employeeList = dao.allEmployee();
        int random = new Random().nextInt(1, employeeList.size() + 1);


        if (productList.isEmpty()) {
            for (Employee employee : employeeList) {
                if (employee.getId() == random) {
                    System.out.println("[" + employee.getName() + "]: Для вас нет посылки!");
                    return;
                }
            }
        }

        if (session.getUser().getBalance() < resultPrice(session.getUser())) {
            System.out.println("[System]: У вас недостаточно средств!");
            return;
        }

        int newPrice = resultPrice(session.getUser());
        payPackageProduct(newPrice);

        dao.updatePriceProduct(session.getUser(), 0);

        if (dao.removeProduct(session.getUser())) {
            for (Employee employee : employeeList) {
                if (employee.getId() == random) {
                    System.out.println("[System]: Посылка успешно была выдана!");
                }
            }
        }
    }

    @Override
    public int resultPrice (User user) {
        List<Product> productList = dao.allProduct(user);

        if (productList.isEmpty()) {
            System.out.println("[System]: Для вас нет посылки!");
        }

        int price = 0;

        for (Product product : productList) {
            price += product.getPrice();
        }

        return price;
    }

    public void payPackageProduct (int price) {
        if (session.getUser().getBalance() < price) {
            System.out.println("[System]: У вас недостаточно средств!");
            return;
        }

        int result = session.getUser().getBalance() - price;

        if (dao.setNewBalanceUser (session.getUser(), result)) {
            session.getUser().setBalance(result);
        }
    }

    public void fakeNumberPackage () {
        System.out.println("[Фуфишмерт Злодей]: Хм... Я думал, ты хочешь что-то другое...");
        System.out.println("[Фуфишмерт Злодей]: Все также, как и в магазине. Повторю на всякий, если забыл.");
        System.out.println("[Фуфишмерт Злодей]: Тебе необходимо повторить номер посылки, пример -> `81c78e5b-e644-4f6c-b391-55d6c5790018`");

        System.out.print("[Фуфишмерт Злодей]: Введи номер посылки: ");
        String fakeNumberPackageString = CONSOLE.nextLine().trim();

        if (fakeNumberPackageString.isBlank()) {
            System.out.println("[System]: Вы ввели пустую строку!");
            return;
        }

        List<Employee> employeeList = dao.allEmployee();
        List<Package> packageList = dao.allPackage();

        int random = new Random().nextInt(1, employeeList.size() + 1);
        System.out.println();

        try {
            for (Employee employee : employeeList) {
                if (employee.getId() == random) {
                    Thread.sleep(1000);
                    System.out.println("[" + session.getUser().getName() +"]: Здравствуйте. Хочу получить посылочку по данному номеру: " + fakeNumberPackageString);
                    Thread.sleep(500);
                    System.out.println("[" + employee.getName() +"]: Здравстуйте. Хм, подождите немного.");

                    Thread.sleep(1500);
                    for (Package packageLocal : packageList) {
                        if (packageLocal.getUuid().equalsIgnoreCase(fakeNumberPackageString)) {
                            System.out.println("[" + employee.getName() +"]: О, это ваша посылка! Вот она: " + packageLocal.getUuid());
                            Thread.sleep(500);
                            System.out.println("[" + employee.getName() + "]: Вот полная информация по вашей посылке: " + packageLocal);
                            Thread.sleep(300);
                            System.out.println();
                            System.out.println("[" + session.getUser().getName() +"]: Спасибо!");
                            Thread.sleep(1500);
                            System.out.println("[Фуфишмерт Злодей]: Красава, ты справился!");
                            return;
                        }
                    }

                    Thread.sleep(2000);
                    System.out.println("[" + employee.getName() + "]: Странно, посылки по такому номеру нету. Вы меня обманываете?");
                    Thread.sleep(500);
                    System.out.println("[" + employee.getName() +"]: Я вызываю Перри-Утканоса! Мне кажется, что вы пытались обмануть меня.");
                    Thread.sleep(500);
                    System.out.println("[Фуфишмерт Злодей]: Молодец, теперь нас поймает Перри-Утканос!");
                    Thread.sleep(500);
                    System.out.println("[CONSOLE]: Вы проиграли!");
                    return;
                }
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }

    public void attemptedTheft () {

        if (counter >= 5) {
            System.out.println("[CONSOLE]: Вы были задержаны за частые попытки кражи. Перри-Утканос скоро прибудет!");
            System.out.println("[CONSOLE]: Вы были задержаны!");
            System.exit(0);
        }

        List<Employee> employeeList = dao.allEmployee();
        List<Package> packageList = dao.allPackage();
        List<String> texts = new ArrayList<>();

        texts.add("[Фуфишмерт Злодей]: Отлично, попробуем что-нибудь попроще.");
        texts.add("[Фуфишмерт Злодей]: И так, как же мы работаем?");
        texts.add("[Фуфишмерт Злодей]: Я отвлекаю сотрудника, а ты украдешь посылку.");
        texts.add("[Фуфишмерт Злодей]: Тут все зависит от удачи, потому что он может и не заметит, а может и поймает.");
        texts.add("[Фуфишмерт Злодей]: Удачи тебе!");

        int randomEmployee = new Random().nextInt(1, employeeList.size() + 1);
        int random = new Random().nextInt(1, packageList.size() + 1);
        int count = 5;

        try {
            for (String text : texts) {
                Thread.sleep(1500);
                System.out.println(text);
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }

        System.out.println();
        System.out.println("[Фуфишмерт Злодей]: Начинаем!");

        try {
            for (Employee employee : employeeList) {
                if (employee.getId() == randomEmployee) {
                    System.out.println("[Фуфишмерт Злодей]: Здравствуйте, можете мне помочь?");
                    Thread.sleep(500);
                    System.out.println("[" + employee.getName() + "]: Здравствуйте, чем могу вам помочь?");
                    Thread.sleep(500);
                    System.out.println("[Фуфишмерт Злодей]: Я хотел бы заказать посылку, но не знаю, как это можно сделать. Поможете?");
                    Thread.sleep(500);
                    System.out.println("[" + employee.getName() + "]: Конечно, я могу помочь вам с этим. Давайте начнем.");
                    Thread.sleep(1500);
                    System.out.println("[CONSOLE]: Вам необходимо подобрать номер для посылки.");

                    while (true) {
                        System.out.print("[CONSOLE]: Введите номер: ");
                        int number = Integer.parseInt(CONSOLE.nextLine().trim());

                        if (number <= 0) {
                            System.out.println("[System]: Вы ввели отрицательное или число равное нулю!");
                            continue;
                        }

                        if (count == 0) {
                            System.out.println("[" + employee.getName() + "]: Я видел, вы пытались украсть посылку!");
                            Thread.sleep(1000);
                            System.out.println("[" + employee.getName() +"]: Я вызываю Перри-Утканоса!");
                            System.out.println("[CONSOLE]: Проигрыш!");
                            return;
                        }

                        if (number == random) {
                            System.out.println("[CONSOLE]: Повезло, вы забрали посылку: " + packageList.get(random - 1).toString());
                            Thread.sleep(1500);
                            System.out.println("[Фуфишмерт Злодей]: Молодец. Ты справился с задачей!");
                            return;
                        }

                        else {
                            System.out.println("[CONSOLE]: Увы, но не тот номер. Попробуй снова. У вас осталось " + count + " попыток!");
                            count--;
                            counter++;
                        }
                    }
                }
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }

        catch (NumberFormatException e) {
            GlobalExceptionHandler.handle(20, e);
        }

        catch (Exception e) {
            GlobalExceptionHandler.handle(88, e);
        }
    }

    public void givePackage() {
        List<User> userList = dao.allUsers();
        List<Package> packageList = dao.allPackage();

        if (userList.isEmpty() || packageList.isEmpty()) {
            System.out.println("[System]: Нет пользователей или посылок");
            return;
        }

        int randomUserIndex = new Random().nextInt(userList.size());
        int randomPackageIndex = new Random().nextInt(packageList.size());

        User randomUser = userList.get(randomUserIndex);
        Package randomPackage = packageList.get(randomPackageIndex);

        try {
            System.out.println("[" + randomUser.getName() + "]: Здравствуйте. Я хочу получить посылку. Вот номер: " + randomPackage.getUuid());
            Thread.sleep(1000);
            System.out.println("[" + session.getUser().getName() + "]: Секундочку. Мне надо найти его. Как найду, так сразу вам его выдам.");
            Thread.sleep(1000);

            System.out.println("[" + session.getUser().getName() + "]: Ага, нашел.");
            Thread.sleep(500);
            System.out.println("Держите!");
            System.out.println(randomPackage);

        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }

    public void sortPackagePrice () {
        try {
            System.out.println("[" + session.getUser().getName() +"]: Ох, много предстоит работы");
            System.out.println("[CONSOLE]: Сортирует по цене: ");
            Thread.sleep(5000);
            System.out.println("[" + session.getUser().getName() + "]: Фух, наконец я закончил. А вот и результат: ");
            List<Package> packageList = dao.allPackage();

            List<Package> newListPackage = packageList.stream()
                    .sorted(Comparator.comparingInt(Package::getPrice))
                    .toList();

            for (Package packageLocal : newListPackage) {
                Thread.sleep(500);
                System.out.println(packageLocal.toString());
                System.out.println();
            }

            System.out.println();
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }

    public void sortPackageClient () {
        try {
            System.out.println("[" + session.getUser().getName() +"]: Ох, много предстоит работы");
            System.out.println("[CONSOLE]: Сортирует по имени клиента: ");
            Thread.sleep(5000);
            System.out.println("[" + session.getUser().getName() + "]: Фух, наконец я закончил. А вот и результат: ");

            List<Package> packageList = dao.allPackage();

            List<Package> newListPackage = packageList.stream()
                    .sorted(Comparator.comparing(Package::getAddressee))
                    .toList();

            for (Package packageLocal : newListPackage) {
                Thread.sleep(500);
                System.out.println(packageLocal.toString());
                System.out.println();
            }

            System.out.println();
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }
}