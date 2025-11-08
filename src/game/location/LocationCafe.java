package game.location;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.service.CafeOrder;
import game.service.Session;

import java.util.Random;
import java.util.Scanner;

public class LocationCafe {
    private final Session session;
    private final Scanner CONSOLE = new Scanner(System.in);
    private final DAO dao;

    public LocationCafe(DAO dao, Session session) {
        this.session = session;
        this.dao = dao;
    }

    public void start () {
        System.out.println();
        switch (session.getGameRole().toString()) {
            case "CLIENT" -> menuClient();
            case "SCAMMER" -> menuScammer();
            case "EMPLOYEE" -> menuEmployee();
            default -> {
                System.out.println("[System]: Ошибка: Неизвестная роль игрока.");
                start();
            }
        }
    }

    public void menuClient () {
        int random = new Random().nextInt(1, 24);

        if (random > 0 && random < 8) {
            System.out.println("[System]: Приносим наши извинения, на данный момент кафе закрыто!");
            return;
        }

        CafeOrder cafeOrder = new CafeOrder(session, dao);
        boolean flag = true;

        try {
            System.out.println();
            System.out.println("[Паймин подружайка]: Ура, мы в кафе!");
            Thread.sleep(1000);
            System.out.println("[Паймин подружайка]: Здесь так вкусно пахнет...");
            Thread.sleep(1000);

            while (flag) {
                System.out.println("[Паймин подружайка]: Давай закажем что-нибудь поесть?");
                System.out.print("[CONSOLE]: Ваш ответ (Да/Нет): ");
                String answer = CONSOLE.nextLine();

                if (answer.equalsIgnoreCase("Да")) {
                    System.out.println();
                    System.out.println("[Паймин подружайка]: Ура! Я хочу сырники.");
                    flag = false;
                } else if (answer.equalsIgnoreCase("Нет")) {
                    System.out.println("[Паймин подружайка]: Ох, ну ладно. Может в следующий раз(");
                    return;
                } else {
                    System.out.println("[Паймин подружайка]: Я не совсем поняла твой ответ, попробуй еще раз.");
                }
            }

            while (true) {
                System.out.println();
                System.out.println("[Паймин подружайка]: Меню выбора: ");
                System.out.println("""
                        1. Посмотреть меню
                        2. Добавить блюдо в заказ
                        3. Убрать блюдо с заказа
                        4. Посмотреть заказ
                        5. Оплатить заказ
                        6. Посмотреть свой профиль
                        7. Сменить локацию
                        """);
                System.out.print("[Паймин подружайка]: Выбирай: ");

                try {
                    int choice = Integer.parseInt(CONSOLE.nextLine());
                    System.out.println();

                    if (choice <= 0) {
                        System.out.println("[System]: Введенное число отрицательное или равно нулю!");
                    }

                    switch (choice) {
                        case 1 -> cafeOrder.allMenu();
                        case 2 -> cafeOrder.addProduct ();
                        case 3 -> cafeOrder.removeProduct ();
                        case 4 -> cafeOrder.checkOrder ();
                        case 5 -> cafeOrder.payOrder ();
                        case 6 -> {
                            System.out.println("[CONSOLE]: Ваш профиль: ");
                            System.out.println(session.getUser().toString());
                        }

                        case 7 -> {
                            return;
                        }

                        default -> {
                            System.out.println("[System]: Неверный выбор. Пожалуйста, выбери номер из меню.");
                            System.out.println("[Паймин подружайка]: Попробуй еще раз.");
                        }
                    }
                }

                catch (NumberFormatException e) {
                    GlobalExceptionHandler.handle(20, e);
                }
            }
        }

        catch (Exception e) {
            GlobalExceptionHandler.handle(21, e);
        }
    }

    public void menuScammer () {
        CafeOrder cafeOrder = new CafeOrder(session, dao);
        boolean flag = true;

        try {
            System.out.println("[Фуфишмерт Злодей]: Приветствую, " + session.getUser().getName() + "!");
            Thread.sleep(2000);
            System.out.println("[Фуфишмерт Злодей]: Ленивый разработчик не смог придумать и сделать нам большой выбор, поэтому будем работать с тем, что есть.");

            while (flag) {
                System.out.println();
                System.out.println("[Фуфишмерт Злодей]: Выбирай, что мы сделаем в кафе.");
                System.out.println("""
                        1. Подделать чек
                        2. Подделать промокод
                        3. Сменить локацию
                        """);
                System.out.print("[Фуфишмерт Злодей]: Твой выбор: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное или число равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        cafeOrder.scammerNumberCheck (session.getUser());
                    }

                    case 2 -> {
                        cafeOrder.scammerPromoCode (session.getUser());
                    }

                    case 3 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Вы выбрали несуществующий вариант! Попробуйте снова!");
                    }
                }
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }

    public void menuEmployee () {
        CafeOrder cafeOrder = new CafeOrder(session, dao);
        cafeOrder.startEmployee();
    }
}