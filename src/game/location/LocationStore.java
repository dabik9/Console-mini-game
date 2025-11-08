package game.location;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.service.Session;
import game.service.StoreOrder;

import java.util.Scanner;

public class LocationStore {
    private final DAO dao;
    private final Session session;
    private final Scanner CONSOLE = new Scanner(System.in);

    public LocationStore (DAO dao, Session session) {
        this.dao = dao;
        this.session = session;
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
        StoreOrder storeOrder = new StoreOrder(dao, session);
        System.out.println("[Паймин подружайка]: Будем забирать посылочки с wb?)");
        while (true) {
            System.out.println();
            System.out.println("[Паймин подружайка]: Вот твои возможности в магазине: ");
            System.out.println("""
                1. Посмотреть товары
                2. Посмотреть, есть ли посылочки на выдаче
                3. Забрать посылку
                4. Сделать заказ
                5. Сменить локацию
                """);
            try {
                System.out.print("[Паймин подружайка]: Выбери действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine());

                if (choice <= 0) {
                    System.out.println("System: Вы ввели отрицательное или равное нулю число!");
                    menuClient();
                }

                switch (choice) {
                    case 1 -> storeOrder.allProductInStore();
                    case 2 -> storeOrder.allProductUser();
                    case 3 -> storeOrder.removeProduct();
                    case 4 -> storeOrder.addProduct();
                    case 5 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Неверный выбор. Пожалуйста, выбери номер из меню.");
                        System.out.println("[Паймин подружайка]: Попробуй еще раз.");
                        menuClient();
                    }
                }
            }

            catch (NumberFormatException e) {
                GlobalExceptionHandler.handle(20, e);
            }
        }
    }

    public void menuScammer () {
        try {
            StoreOrder storeOrder = new StoreOrder(dao, session);
            System.out.println("[Паймин подружайка]: Мне жаль, что ты выбрал сторону зла. Пока(");
            Thread.sleep(1000);
            System.out.println("[Фуфишмерт Злодей]: Что ж. Приветствую, " + session.getUser().getName() + "!");
            Thread.sleep(1000);
            System.out.println("[Фуфишмерт Злодей]: Опять разработчику было лень делать нам большой ассортимент выбора, поэтому лови, что у нас доступно: ");

            while (true) {
                System.out.println();
                System.out.println("""
                    1. Подделать чек
                    2. Украсть товар
                    3. Построить большую машину для уничтожения мира!)
                    4. Сменить локацию
                    """);
                System.out.print("[Фуфишмерт Злодей]: Твой выбор: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное или число равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> storeOrder.fakeNumberPackage();
                    case 2 -> storeOrder.attemptedTheft();
                    case 3 -> {
                        System.out.println("[CONSOLE]: Если хотите обновление, то поддержите разработчика, чтобы он реализовал больше функций, персонажей, возможностей и других функций в игре)");
                        Thread.sleep(500);
                        System.out.println("[CONSOLE]: Пока нет возможности создать машину для уничтожения мира(");
                    }

                    case 4 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Вы ввели неизвестное значение. Введите 1, 2, 3 или 4.");
                        menuScammer();
                    }
                }
            }
        }

        catch (NumberFormatException e) {
            GlobalExceptionHandler.handle(20, e);
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }

        catch (Exception e){
            GlobalExceptionHandler.handle(88, e);
        }
    }

    public void menuEmployee () {
        StoreOrder storeOrder = new StoreOrder(dao, session);
        System.out.println("[Жибер Начальик]: Приветствую, " + session.getUser().getName() + " на складе wb. Тут бывает много посетителей.");
        while (true) {
            System.out.println();
            System.out.println("[Жибер Начальик]: Меню склада:");
            System.out.println("""
                    1. Отдать посылку
                    2. Отсортировать по цене
                    3. Отсортировать по имени клиента
                    4. Сменить локацию
                    """);
            try {
                System.out.print("[Жибер Начальик]: Введи номер действия: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное значение. Введите число больше нуля.");
                    continue;
                }

                switch (choice) {
                    case 1 -> storeOrder.givePackage();
                    case 2 -> storeOrder.sortPackagePrice();
                    case 3 -> storeOrder.sortPackageClient();
                    case 4 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Вы ввели неизвестное значение. Введите 1, 2 или 3.");
                        menuEmployee();
                    }
                }
            }

            catch (NumberFormatException e) {
                GlobalExceptionHandler.handle(20, e);
            }
        }
    }
}