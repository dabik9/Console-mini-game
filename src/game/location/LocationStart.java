package game.location;

import game.dao.DAO;
import game.exceptions.BaseException;
import game.exceptions.GlobalExceptionHandler;
import game.model.enums.GameRole;
import game.service.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LocationStart {
    private final Session session;
    private final Scanner CONSOLE = new Scanner(System.in);
    private final DAO dao;

    public LocationStart(DAO dao, Session session) {
        this.session = session;
        this.dao = dao;
    }

    public void start () {
        System.out.println("[Паймин подружайка]: Привет-привет, " + session.getUser().getName() + "! Добро пожаловать!");
        System.out.println("[Паймин подружайка]: Нам надо сначала определиться с твоей ролью в игре.");
        menu();
    }

    public void menu () {
        System.out.println("Выберите роль: ");
        System.out.println("""
                1. Клиент
                
                2. Мошенник
                
                3. Сотрудник
                """);

        System.out.print("[Паймин подружайка]: Выбери свою роль (Введи номер): ");
        try {
            int choice = Integer.parseInt(CONSOLE.nextLine());

            switch (choice) {
                case 1 -> session.setGameRole(GameRole.CLIENT);
                case 2 -> {
                    System.out.println("[Паймин подружайка]: Оу, жаль, пока(");
                    System.out.println();
                    session.setGameRole(GameRole.SCAMMER);
                }
                case 3 -> session.setGameRole(GameRole.EMPLOYEE);
                default -> {
                    System.out.println("[System]: Неверный выбор. Пожалуйста, выбери 1, 2 или 3. Новые роли пока не добавлены.");
                    menu();
                    return;
                }
            }
        }

        catch (BaseException e) {
            GlobalExceptionHandler.handle(e);
        }

        location();
    }

    public void location () {
        while (true) {
            if (session.getGameRole().equals(GameRole.CLIENT)) {
                System.out.println("[Паймин подружайка]: Отлично! Куда хочешь отправиться?");
            } else if (session.getGameRole().equals(GameRole.SCAMMER)) {
                System.out.println("[Фуфишмерт Злодей]: Отлично, куда хочешь отправиться творить злодейства?");
            } else if (session.getGameRole().equals(GameRole.EMPLOYEE)) {
                System.out.println("[Жибер Начальик]: Отлично, куда хочешь отправиться работать?");
            }

            else {
                System.out.println("[System]: Ошибка!");
                return;
            }

            System.out.println();
            System.out.println("""
                1. Кафе
                2. Склад wb
                3. Зоопарк
                4. Посмотреть свой профиль
                5. Информация о разработчике
                6. Завершить игру!
                """);
            if (session.getGameRole().equals(GameRole.CLIENT)) {
                System.out.print("[Паймин подружайка]: Выбери номер локации: ");
            } else if (session.getGameRole().equals(GameRole.SCAMMER)) {
                System.out.print("[Фуфишмерт Злодей]: Выбери локацию: ");
            } else if (session.getGameRole().equals(GameRole.EMPLOYEE)) {
                System.out.print("[Жибер Начальик]: Выбери локацию: ");
            }
            try {
                int choice = Integer.parseInt(CONSOLE.nextLine());

                switch (choice) {
                    case 1 -> {
                        LocationCafe locationCafe = new LocationCafe(dao, session);
                        locationCafe.start();
                    }

                    case 2 -> {
                        LocationStore locationStore = new LocationStore (dao, session);
                        locationStore.start();
                    }

                    case 3 -> {
                        LocationZoo locationZoo = new LocationZoo (session, dao);
                        locationZoo.start();
                    }

                    case 4 -> System.out.println(session.getUser().toString());

                    case 5 -> {
                        List<String> infoADeveoper = new ArrayList<>();
                        infoADeveoper.add("[Даня<3]: Оу, это так мило, что вы решили ознакомиться с информацией обо мне");
                        infoADeveoper.add("[Даня<3]: Меня зовут Даня и мне 18 лет. Являюсь студентом 3 курса колледжа IThub");
                        infoADeveoper.add("[Даня<3]: Пишу на Java и Mysql, в качестве хобби изучаю C#, Unity, приходилось работать с Postgres, Python, XML, HTML, CSS, JS");
                        infoADeveoper.add("[Даня<3]: На данный момент сосредоточен на изучении Java и Spring Boot, а также Spring в будущем. Если речь про разработки, то пока делаю такие мини консольные игры)");
                        infoADeveoper.add("[Даня<3]: Спасибо за внимание<3");

                        try {
                            for (String text : infoADeveoper) {
                                System.out.println(text);
                                Thread.sleep(3500);
                            }
                        }

                        catch (InterruptedException e) {
                            GlobalExceptionHandler.handle(83, e);
                        }

                        System.out.println();
                        location();
                    }

                    case 6 -> {
                        System.out.println("[CONSOLE]: Game over!");
                        System.exit(0);
                    }

                    default -> {
                        System.out.println("[System]: Неверный выбор!");
                        location();
                    }
                }
            }

            catch (BaseException e) {
                GlobalExceptionHandler.handle(e);
            }

            catch (NumberFormatException e) {
                GlobalExceptionHandler.handle(20, e);
            }
        }
    }
}