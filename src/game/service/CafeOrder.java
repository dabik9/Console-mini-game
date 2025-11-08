package game.service;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.model.Dish;
import game.model.Employee;
import game.model.Order;
import game.model.User;
import game.model.enums.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CafeOrder implements Order {
    private final Session session;
    private final DAO dao;
    private static int count = 0;
    private static int counter = 0;
    private final Scanner CONSOLE = new Scanner(System.in);

    public CafeOrder (Session session, DAO dao) {
        this.session = session;
        this.dao = dao;
    }

    public void addDish (Dish dish) {
        List<Dish> dishes = dao.allDish();
        int numberOrder = dao.numberOrderUser(session.getUser());

        for (Dish dishLocal : dishes) {
            if (dishLocal.getId() == dish.getId()) {
                if (counter <= 10) {
                    if (dao.addDishOrder(numberOrder, dish.getId(), 1, dish.getPrice())) {
                        System.out.println("[System]: Вы успешно добавили блюдо: " + dish.getName() + " в заказ!");
                        counter++;
                    }
                }

                else {
                    System.out.println("[System]: Вы добавили уже 10 блюд, чтобы добавить больше необходимо обратиться к сотруднику!");
                }
            }
        }
    }

    @Override
    public int resultPrice (User user) {
        List<Dish> dishes = dao.allListDishOrder(dao.numberOrderUser(user));
        int price = 0;

        if (dishes.isEmpty()) {
            System.out.println("[System]: Ошибка. Ваш заказ пустой!");
            return -1;
        }

        for (Dish dish : dishes) {
            price += dish.getPrice();
        }

        return price;
    }

    public void allMenu () {

        try {
            List<Dish> dishes = dao.allDish();

            if (dishes.isEmpty()) {
                System.out.println("[System]: В данный момент посмотреть меню невозможно. Попробуйте позже!");
            }

            for (Dish dish : dishes) {
                Thread.sleep(500);
                System.out.println("Id: " + dish.getId() + ", название: " + dish.getName() + ", цена: " + dish.getPrice() + ", категория: " + dish.getCategory());
            }

            System.out.println();
            Thread.sleep(2000);
            System.out.println("[Паймон]: Ой, как много всего вкусного)");
            System.out.println();
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }

    @Override
    public void addProduct () {
        System.out.println("[Паймон]: Надо ввести номер блюда, который хочешь добавить в заказ)");
        System.out.print("[Паймон]: Введи номер блюда, чтобы добавить его в заказ: ");

        try {
            int numberDish = Integer.parseInt(CONSOLE.nextLine().trim());

            if (numberDish <= 0) {
                System.out.println("[System]: Выбранный номер блюда отрицательный или равен нулю!");
                System.out.println();
                return;
            }

            List<Dish> dishList = dao.allDish();

            for (Dish dish : dishList) {
                if (dish.getId() == numberDish) {
                    addDish(dish);
                    dao.updatePriceOrder(session.getUser(), resultPrice(session.getUser()));
                    return;
                }
            }

            System.out.println("[System]: Блюдо с id: " + numberDish + " не было найдено в меню!");
            System.out.println();
        }

        catch (NumberFormatException e) {
            GlobalExceptionHandler.handle(20, e);
        }
    }

    @Override
    public void removeProduct () {
        System.out.println("[Паймон]: Оу, ты выбрал что-то лишнее?");
        System.out.print("[Паймон]: Введи номер блюда, который хочешь убрать из заказа: ");

        try {
            int numberDish = Integer.parseInt(CONSOLE.nextLine().trim());

            if (numberDish <= 0) {
                System.out.println("[System]: Выбранный номер блюда отрицательный или равен нулю!");
                System.out.println();
                System.out.println();
                return;
            }

            List<Dish> dishList = dao.allListDishOrder(session.getUser().getId());

            for (Dish dish : dishList) {
                if (dish.getId() == numberDish) {
                    dao.removeDishOrder(numberDish, dao.numberOrderUser(session.getUser()));
                    return;
                }
            }

            System.out.println("[System]: В вашем заказе не найдено блюдо с id: " + numberDish + "!");
        }

        catch (Exception e) {
            GlobalExceptionHandler.handle(20, e);
        }
    }

    public void checkOrder () {
        System.out.println("[Паймон]: Давай посмотрим, что мы уже заказали.");

        List<Dish> dishes = dao.allListDishOrder(session.getUser().getId());

        if (dishes.isEmpty()) {
            System.out.println("[System]: Вы ничего не добавили в заказ!");

            if (count > 5) {
                System.out.println("[Паймон]: Мы ничего не добавили, поэтому там ничего и нет, зачем проверять каждый раз, как холодильник?");
                System.out.println();
                return;
            }

            System.out.println("[Паймон]: Ой, да, мы забыли добавить в заказ блюда, давай добавим сырники?");
            System.out.println();
            count++;
            return;
        }

        boolean flag = false;
        System.out.println("[System]: Ваш заказ: ");
        System.out.println("[System]: Номер заказа: " + dao.getNumberOrder(session.getUser()));

        for (Dish dish : dishes) {
            System.out.println("Id: " + dish.getId() + ", категория: " + dish.getCategory() + ", имя блюда: " + dish.getName() + ", цена: " + dish.getPrice());

            if (dish.getName().equals("Сырники")) {
                flag = true;
            }
        }

        System.out.println();
        System.out.println("[CONSOLE]: Итого с Вас: " + resultPrice(session.getUser()));
        System.out.println();

        if (flag) {
            System.out.println("[Паймон]: О, это сырники! Спасибо, спасибо, спасибо! >3");
        }

        else {
            System.out.println("[Паймон]: Хм, а где же мои любимые сырники? Может добавим их в заказ?");
        }

        System.out.println();

    }

    public void payOrder () {
        List<Dish> dishes = dao.allListDishOrder(session.getUser().getId());

        if (dishes.isEmpty()) {
            System.out.println("[System]: Вы не можете оплатить пустой заказ. Закажите что-нибудь!");
            System.out.println("[Паймон]: Давай закажем сырники тогда?");
            System.out.println();
            return;
        }

        int totalPrice = resultPrice(session.getUser());
        boolean flag = false;

        if (totalPrice == 0 || totalPrice == -1) {
            System.out.println("[System]: Ошибка при подсчете суммы заказа!");
            return;
        }

        if (session.getUser().getBalance() < totalPrice) {
            System.out.println("[System]: У вас недостаточно средств для оплаты заказа!");
            System.out.println("[Паймон]: Ой, похоже у нас не хватает денег(");
            return;
        }

        session.getUser().setBalance(session.getUser().getBalance() - totalPrice);

        if (dao.setNewBalanceUser(session.getUser(), session.getUser().getBalance())) {
            System.out.println("[CONSOLE]: Баланс успешно обновлен!");
        }

        else {
            System.out.println("[System]: Ошибка при обновлении баланса!");
            return;
        }

        for (Dish dish : dishes) {
            if (dao.removeDishOrder(dish.getId(), dao.numberOrderUser(session.getUser()))) {
                flag = true;
            }
        }

        if (flag) {
            System.out.println("[CONSOLE]: Вы успешно оплатили заказ на сумму: " + totalPrice + " рублей.");
            System.out.println("[CONSOLE]: Оставшийся баланс: " + session.getUser().getBalance() + " рублей.");
            System.out.println("[Паймон]: Ура! Теперь мы можем спокойно насладиться!");
        }
    }

    /*
    *
    *
    * */

    public void scammerNumberCheck (User user) {
        System.out.println("[Фуфишмерт Злодей]: Вот пример того, как может выглядеть номер чека -> `eb5be540-4c29-4d16-b73d-a061a517bb06`.");
        System.out.println("[Фуфишмерт Злодей]: Главное подделать так, чтобы на кассе не заметили подмену и не вызвали Перри-Утконоса.");

        System.out.print("[Фуфишмерт Злодей]: Будешь пробывать (Да/Нет): ");
        String answer = CONSOLE.nextLine().trim();

        while (true) {

            if (answer.equalsIgnoreCase("Да")) {
                System.out.println("[Фуфишмерт Злодей]: Отлично!");
                break;
            } else if (answer.equalsIgnoreCase("Нет")) {
                System.out.println("[Фуфишмерт Злодей]: Я так и понял. Риски правда высокие.");
                return;
            }

            else {
                System.out.println("[Фуфишмерт Злодей]: Ответь внятно.");
            }
        }

        System.out.println("Попробуй написать номер чека: " );
        String fakeCheck = CONSOLE.nextLine().trim();

        if (fakeCheck.isBlank()) {
            System.out.println("[System]: Вы ввели пустое значение!");
            return;
        }

        if (fakeCheck.length() != 36) {
            System.out.println("[System]: Строка имеет длину не равную 36!");
            return;
        }

        System.out.println("[Фуфишмерт Злодей]: Отлично, теперь отнеси на кассу и попробуй сделать заказ");

        List<Employee> employeeList = dao.allEmployee();
        int random = new Random().nextInt(1, employeeList.size() + 1);

        System.out.println();

        try {
            for (Employee employee : employeeList) {
                if (random == employee.getId()) {
                    Thread.sleep(800);
                    System.out.println("[" + employee.getName() + "]: Здравствуйте, чем могу Вам помочь?");
                    Thread.sleep(500);
                    System.out.println("[" + session.getUser().getName() + "]: Я хочу получить свой заказ. Вот мой номер заказа -> " + fakeCheck);

                    if (dao.checkFakeNumberOrder(fakeCheck)) {
                        Thread.sleep(2000);
                        System.out.println("[" + employee.getName() + "]: Все хорошо, вот ваш заказ.");
                        try {
                            Thread.sleep(1000);
                        }

                        catch (Exception e) {
                            System.out.println("[System]: Ошибка: " + e.getMessage());
                            return;
                        }

                        System.out.println("[Фуфишмерт Злодей]: Ого, даже мне не удавалось их подделать, у тебя определенно есть талант!");
                    }

                    else {
                        Thread.sleep(2000);
                        System.out.println("[" + employee.getName() + "]: В базе данных нет такого заказа. Вы меня пытались обмануть?");
                        try {
                            Thread.sleep(1000);
                        }

                        catch (InterruptedException e) {
                            GlobalExceptionHandler.handle(83, e);
                        }

                        System.out.println("[" + employee.getName() + "]: Я вызываю Перри-Утконоса!");
                        Thread.sleep(700);
                        System.out.println("[CONSOLE]: Вас поймал и отправил в тюрьму Перри-Утконос.");
                        System.exit(0);
                    }
                }
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }

    public void scammerPromoCode (User user) {
        try {
            System.out.println("[Фуфишмерт Злодей]: Так, нам необходимо будет сделать заказ и подделать промокод, чтобы получить скидку в 100%");
            Thread.sleep(500);
            System.out.println("[Фуфишмерт Злодей]: Промокод выглядит примерно так -> 04.01.2003 или 04.01");
            Thread.sleep(500);
            System.out.println("[Фуфишмерт Злодей]: В основном это какие-то даты, поэтому угадать будет не сложно");
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }

        System.out.print("[Фуфишмерт Злодей]: Введи какой-нибудь промокод: ");
        String fakePromoCod = CONSOLE.nextLine().trim();

        if (fakePromoCod.isBlank()) {
            System.out.println("[System]: Вы ввели пустое значение!");
            return;
        }

        if (fakePromoCod.length() != 10 && fakePromoCod.length() != 5) {
            System.out.println("[System]: Строка имеет длину не равную 10 или 5!");
            return;
        }

        List<Employee> employeeList = dao.allEmployee();
        List<String> promoCodes = new ArrayList<>();

        promoCodes.add("23-05-1995");
        promoCodes.add("01-01-1983");
        promoCodes.add("01-04-1976");
        promoCodes.add("04-09-1998");
        promoCodes.add("04-02-2004");
        promoCodes.add("21-07-2010");
        promoCodes.add("06-03-2009");
        promoCodes.add("12-03-1989");
        promoCodes.add("26-02-1991");
        promoCodes.add("15-01-2001");

        promoCodes.add("12-04-1961");
        promoCodes.add("20-07-1969");
        promoCodes.add("09-05-1945");
        promoCodes.add("04-10-1957");

        promoCodes.add("01-01");
        promoCodes.add("07-01");
        promoCodes.add("23-02");
        promoCodes.add("08-03");
        promoCodes.add("01-05");
        promoCodes.add("09-05");
        promoCodes.add("12-06");
        promoCodes.add("04-11");

        promoCodes.add("01-09");
        promoCodes.add("31-10");
        promoCodes.add("25-12");
        promoCodes.add("14-02");
        promoCodes.add("01-04");

        promoCodes.add("12-12-2024");
        promoCodes.add("11-11-2024");

        int random = new Random().nextInt(1, employeeList.size() + 1);

        try {
            for (Employee employee : employeeList) {
                if (random == employee.getId()) {
                    System.out.println("[" + employee.getName() + "]: Здравствуйте, чем могу вам помочь?");
                    Thread.sleep(700);
                    System.out.println("[" + session.getUser().getName() + "]: Хочу забрать свой заказ");
                    Thread.sleep(1000);
                    System.out.println("[" + employee.getName() + "]: Хорошо, вы уже оплатили или будете сейчас оплачивать?");
                    Thread.sleep(700);
                    System.out.println("[" + session.getUser().getName() + "]: Нет, хочу здесь. Стоп, у меня есть прокомод -> " + fakePromoCod);

                    for (String promoCod : promoCodes) {
                        if (promoCod.equalsIgnoreCase(fakePromoCod)) {
                            Thread.sleep(700);
                            System.out.println("[" + employee.getName() + "]: Оу, вот как. Поздравляю. Вы получаете заказ бесплатно!");
                            Thread.sleep(800);
                            System.out.println("[Фуфишмерт Злодей]: Отличная работа!");
                            return;
                        }
                    }

                    Thread.sleep(1000);
                    System.out.println("[" + employee.getName() + "]: Указанный вами промокод отсутствует у меня в списках. Вы пытались меня обмануть?");
                    Thread.sleep(400);
                    System.out.println("[" + employee.getName() + "]: Охрана! Задержать их. Я вызываю Перри-Утконоса!!!");

                    for (Employee employeeLocal : employeeList) {
                        if (employeeLocal.getRole().equals("SECURITY_GUARD")) {
                            Thread.sleep(600);
                            System.out.println("[" + employeeLocal.getName() + "]: Вы задержаны!");
                        }
                    }

                    Thread.sleep(700);
                    System.out.println("[CONSOLE]: Вы были задержаны Перри-Утконосом!");
                    return;
                }
            }
        }

        catch (InterruptedException e){
            GlobalExceptionHandler.handle(83, e);
        }

        System.out.println();
    }

    /*
    *
    * Сотрудник
    *
    * */

    public void startEmployee () {
        System.out.println();
        switch (session.getUser().getRole()) {
            case Role.USER -> changeRole ();
            case Role.REGULAR_USER -> changeRole();
            case Role.EMPLOYEE -> cashierWork ();
            case Role.SECURITY_GUARD -> securityGuardWork ();
            case Role.MANAGER -> managerWork ();
            case Role.ADMINISTRATOR -> administratorWork ();
            case Role.POLICE_OFFICER -> System.out.println("[CONSOLE]: В активной разработке! Пока рекомендуется сменить роль.");
            default -> System.out.println("[System]: Не удалось определить вашу роль! Рекомендация сменить аккаунт, для возможности поиграть за выбранную игровую роль на данной локации.");
        }
    }

    public void changeRole () {
        System.out.println("[CONSOLE]: Оу, ваша роль: " + session.getUser().getRole() + ". Необходимо ее сменить на роль сотрудника, чтобы поиграть на данной локации!");
        while (true) {
            System.out.println("[Жибер Начальик]: Выберите роль сотрудника из списка ниже: ");
            System.out.println("""
                            1. Кассир
                            2. Охранник
                            3. Менеджер
                            4. Администратор
                            """);
            try {
                System.out.print("[Жибер Начальик]: Введите номер роли, которую хотите получить: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0 ) {
                    System.out.println("[System]: Вы ввели отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        session.getUser().setRole(Role.EMPLOYEE);
                        dao.setNewRoleUser(Role.EMPLOYEE, session.getUser());
                        System.out.println("[CONSOLE]: Вы сменили роль на Кассира!");
                        return;
                    }

                    case 2 -> {
                        session.getUser().setRole(Role.SECURITY_GUARD);
                        dao.setNewRoleUser(Role.SECURITY_GUARD, session.getUser());
                        System.out.println("[CONSOLE]: Вы сменили роль на Охранника!");
                        return;
                    }

                    case 3 -> {
                        session.getUser().setRole(Role.MANAGER);
                        dao.setNewRoleUser(Role.MANAGER, session.getUser());
                        System.out.println("[CONSOLE]: Вы сменили роль на Менеджера!");
                        return;
                    }

                    case 4 -> {
                        System.out.println("[CONSOLE]: Вам необходимо ввести код, чтобы получить столь высокую роль: ");
                        int cod = Integer.parseInt(CONSOLE.nextLine().trim());

                        if (!(cod == 456)) {
                            System.out.println("[System]: Вы ввели неверный код. Сменить роль на " + Role.ADMINISTRATOR + " не удалось!");
                            continue;
                        }

                        session.getUser().setRole(Role.ADMINISTRATOR);
                        dao.setNewRoleUser(Role.ADMINISTRATOR, session.getUser());
                        System.out.println("[CONSOLE]: Вы сменили роль на Администратора!");
                        return;
                    }

                    default -> System.out.println("[System]: Вы ввели неизвестную роль. Попробуйте снова!");
                }
            }

            catch (Exception e) {
                GlobalExceptionHandler.handle(56, e);
            }
        }
    }

    public String  employeePayOrder (User user, Dish dish) {
        if (user.getBalance() < dish.getPrice()) {
            return "Недостаточно средств для оплаты!";
        }

        if (dao.setNewBalanceUser(user,user.getBalance() - dish.getPrice())) {
            return "Оплата прошла успешно!";
        }
        return "Упс, что-то пошло не так";
    }

    public void cashierWork () {
        System.out.println("[Жибер Начальик]: Итак, ты у нас являешься кассиром, поэтому обслуживай клиентов.");
        System.out.println();

        List<User> userList = dao.allUsers();
        List<Dish> dishList = dao.allDish();
        int randomUser = new Random().nextInt(1, userList.size() + 1);
        int randomDish = new Random().nextInt(1, dishList.size() + 1);

        try {
            for (User user : userList) {
                if (user.getId() == randomUser) {
                    for (Dish dish : dishList) {
                        if (dish.getId() == randomDish) {
                            System.out.println();
                            Thread.sleep(1000);
                            System.out.println("[" + session.getUser().getName() + "]: Здравствуйте, чем могу вам помочь?");
                            Thread.sleep(500);
                            System.out.println("[" + user.getName() + "]: Я хочу заказать " + dish.getName());
                            Thread.sleep(500);
                            System.out.println("[" + session.getUser().getName() + "]: Отлично. С вас: " + dish.getPrice());
                            Thread.sleep(500);
                            System.out.println("[" + session.getUser().getName() +"]: " + employeePayOrder(user, dish));
                            Thread.sleep(500);
                            System.out.println("[" + session.getUser().getName() + "]: Удачи!");
                        }
                    }
                }
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(77, e);
        }
    }

    public void securityGuardWork () {
        System.out.println("[Жибер Начальик]: Ты у нас охранник, твоей задачей является контролем за порядком.");
        System.out.println();

        List<Employee> employeeList = dao.allEmployee();
        int random = new Random().nextInt(1, 36);
        int randomEmployee = new Random().nextInt(1, employeeList.size() + 1);

        if (random > 0 && random < 24) {
            for (Employee employee : employeeList) {
                if (employee.getId() == randomEmployee) {
                    System.out.println("[" + session.getUser().getName() + "]: " + session.getUser().getName() + " задержи его, он мошенник!");

                    try {
                        Thread.sleep(2000);
                    }

                    catch (InterruptedException e) {
                        GlobalExceptionHandler.handle(83, e);
                    }

                    System.out.println("[" + session.getUser().getName() + "]: Поймал! " + employee.getName() + " вызывай Перри-Утконоса.");
                    return;
                }
            }
        }

        else {
            System.out.println("[" + session.getUser().getName() + "]: Какой спокойный день, и без проблем.");
        }
    }

    public void managerWork () {
        System.out.println("[Жибер Начальик]: Менеджер. Это не простая работа, но тебе по силам. Удачи!");
        System.out.println();

        List<User> userList = dao.allUsers();
        List<Employee> employeeList = dao.allEmployee();
        List<Dish> dishList = dao.allDish();

        int randomUser = new Random().nextInt(1, userList.size() + 1);
        int randomEmployee = new Random().nextInt(1, employeeList.size() + 1);
        int randomDish = new Random().nextInt(1, dishList.size() + 1);
        int randomTime = new Random().nextInt(30, 61);

        for (User user : userList) {
            for (Employee employee : employeeList) {
                for (Dish dish : dishList) {
                    if (user.getId() == randomUser) {
                        if (employee.getId() == randomEmployee) {
                            if (dish.getId() == randomDish) {
                                System.out.println("[CONSOLE]: Вас вызывали на кассу!");
                                System.out.println("[" + user.getName() +"]: Здравствуйте. Вы кто?");
                                System.out.println("[" + session.getUser().getName() + "]: Менеджер. Чем могу вам помочь?");
                                System.out.println("[" + user.getName() + "]: Я жду заказ: " + dish.getName() + ", больше, чем полчаса, а если точнее, то " + randomTime + " минут. Где мой заказ?");

                                while (true) {
                                    System.out.println();
                                    System.out.println("[Жибер Начальик]: Вам необходимо дать ответ клиенту, чтобы решить проблему. Выбери один из следующих вариантов");
                                    System.out.println("""
                                                    1. Послать клиента и сказать ему ждать

                                                    2. Попросить вежливо подождать еще, принести извинения и поторопить подчиненных

                                                    3. Попросить охранника выкинуть клиента из кафе
                                                    """);
                                    System.out.println();

                                    try {
                                        System.out.print("[Жибер Начальик]: Ваш выбор: ");
                                        int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                                        if (choice <= 0) {
                                            System.out.println("[System]: Вы ввели отрицательное значение или равное нулю!");
                                            continue;
                                        }

                                        switch (choice) {
                                            case 1 -> {
                                                System.out.println("[" + session.getUser().getName() + "]: Сядьте на свое место и ждите заказ! Больше повторять не буду, если продолжите, то вас выкинут из кафе!");
                                                System.out.println("[" + user.getName() + "]: Это что такое? Я буду жаловаться вашему начальству!");
                                                System.out.println("[" + session.getUser().getName() + "]: Жалуйтесь.");

                                                List<String> answerOwner = new ArrayList<>();
                                                answerOwner.add("[Жибер Начальик]: Это что было сегодня?");
                                                answerOwner.add("[Жибер Начальик]: Такое поведение является максимально непрофессиональным, мы вынуждены с вами попрощаться.");
                                                answerOwner.add("[Жибер Начальик]: Прощайте.");

                                                for (String text : answerOwner) {
                                                    try {
                                                        Thread.sleep(4000);
                                                        System.out.println(text);
                                                    }

                                                    catch (Exception e) {
                                                        System.out.println("[System]: Ошибка: " + e.getMessage());
                                                    }
                                                }

                                                System.out.println("[" + session.getUser().getName() + "]: Видимо не стоило так отвечать...");
                                            }

                                            case 2 -> {
                                                System.out.println("[" + session.getUser().getName() + "]: Приносим вам наши извинения. Я сейчас же попрошу выполнить ваш заказ как можно скорее. Ожидайте");
                                                System.out.println("[" + user.getName() + "]: Хорошо, спасибо");

                                                try {
                                                    Thread.sleep(3000);
                                                    System.out.println("[Жибер Начальик]: Отличная работа. Получай бонус в 3 000 рублей. Продолжай в том же духе!");
                                                    int balance = session.getUser().getBalance();
                                                    balance += 3000;
                                                    dao.setNewBalanceUser(session.getUser(), balance);
                                                    return;
                                                }

                                                catch (Exception e) {
                                                    System.out.println("[System]: Ошибка: " + e.getMessage());
                                                }
                                            }

                                            case 3 -> {
                                                for (Employee employeeLocal : employeeList) {
                                                    if (employeeLocal.getRole().equals("SECURITY_GUARD")) {
                                                        try {
                                                            System.out.println("[" + session.getUser().getName() + "]: " + employeeLocal.getName() + " подойди, пожалуйста сюда");
                                                            Thread.sleep(2500);
                                                            System.out.println("[" + session.getUser().getName() + "]: Выкинь из кафе этого клиента");
                                                            Thread.sleep(2500);
                                                            System.out.println("[" + user.getName() + "]: В каком смысле?");
                                                            Thread.sleep(2500);
                                                            System.out.println("[" + employeeLocal.getName() + "]: Сейчас все сделаю");

                                                            Thread.sleep(4000);
                                                            System.out.println("[Жибер Начальик]: Уволен!");
                                                            return;
                                                        }

                                                        catch (Exception e) {
                                                            System.out.println("[System]: Ошибка: " + e.getMessage());
                                                        }
                                                    }
                                                }

                                                System.out.println("[" + session.getUser().getName() + "]: Эх, печально. Надо было спокойно отвечать...");
                                            }

                                            default -> System.out.println("[System]: Вы ввели неизвестное значение. Введите 1, 2 или 3.");
                                        }
                                    }

                                    catch (NumberFormatException e) {
                                        GlobalExceptionHandler.handle(20, e);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void administratorWork () {
        System.out.println("[Жибер Начальик]: Ты являешься Администратором кафе, а значит на тебя вся ответственность за работу и персонал.");
        System.out.println();

        try {
            Thread.sleep(4000);
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }

        System.out.println("[CONSOLE]: Повезло, отдыхай!");
    }
}