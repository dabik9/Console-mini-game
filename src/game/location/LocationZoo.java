package game.location;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.service.Session;
import game.service.ZooService;

import java.util.Scanner;

public class LocationZoo {
    private final Session session;
    private final DAO dao;
    private final Scanner CONSOLE = new Scanner(System.in);

    public LocationZoo (Session session, DAO dao) {
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
        ZooService zooService = new ZooService(dao, session);
        try {
            System.out.println("[Паймин подружайка]: Ура, мы в зоопарке!");
            System.out.println("[Паймин подружайка]: К какому из животных ты хочешь подойти?");
            while (true) {
                System.out.println();
                System.out.println("""
                        1. Лошадка
                        2. Собачка
                        3. Кошечка
                        4. Тигр
                        5. Попугай
                        6. Сменить локацию
                        """);

                System.out.print("[Паймин подружайка]: Выбери действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное или число равное нулю!");
                    return;
                }

                switch (choice) {
                    case 1 -> zooService.functionHorse();
                    case 2 -> zooService.functionDogs();
                    case 3 -> zooService.functionCats();
                    case 4 -> zooService.functionTiger();
                    case 5 -> zooService.functionParrot();
                    case 6 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Вы ввели неверное число!");
                        menuClient();
                    }
                }
            }
        }

        catch (NumberFormatException e) {
            GlobalExceptionHandler.handle(20, e);
        }
    }

    public void menuScammer () {
        ZooService zooService = new ZooService(dao, session);
        try {
            Thread.sleep(1000);
            System.out.println("[Паймин подружайка]: Эх, прощай(");
            Thread.sleep(2000);
            System.out.println("[Фуфишмерт Злодей]: Приветствую, " + session.getUser().getName() + "!");
            Thread.sleep(1000);
            while (true) {
                System.out.println();
                System.out.println("[Фуфишмерт Злодей]: И так, мы с тобой можем заняться каким-то одним из следующих дел:");
                System.out.println("""
                    1. Украсть автомат с едой
                    2. Добавить фейковое животное
                    3. Сменить локацию
                    """);
                System.out.print("[Фуфишмерт Злодей]: Выберите один из пунктов: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> zooService.stealMachine();
                    case 2 -> zooService.createFakeNamePlate();
                    case 3 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Вы ввели неверный выбор!");
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

    public void menuEmployee () {
        ZooService zooService = new ZooService(dao, session);
        try {
            Thread.sleep(2000);
            System.out.println("[Жибер Начальик]: О, вы как раз вовремя. У меня есть несколько дел для вас.");
            Thread.sleep(800);
            while (true) {
                System.out.println();
                System.out.println("Выбирайте, что сделаете сегодня: ");
                System.out.println("""
                    1. Покормить всех животных
                    2. Продать билеты в зоопарк
                    3. Отсортировать бумажный список животных по имени
                    4. Сменить локацию
                    """);
                System.out.print("[Жибер Начальик]: Твой выбор: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> zooService.feedAllAnimals();
                    case 2 -> zooService.sellTickets();
                    case 3 -> zooService.sortAnimalsByName();
                    case 4 -> {
                        return;
                    }

                    default -> {
                        System.out.println("[System]: Вы ввели неизвестное значение. Введите 1, 2, 3 или 4.");
                        menuEmployee();
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
}