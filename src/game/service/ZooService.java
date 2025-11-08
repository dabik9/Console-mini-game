package game.service;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.model.Animal;
import game.model.Employee;
import game.model.User;
import game.model.enums.CategoryAnimal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ZooService {
    private final DAO dao;
    private final Session session;
    private final Scanner CONSOLE = new Scanner(System.in);
    private int counterHorse = 0;
    private int counterCat = 0;
    private int counterDog = 0;
    private int counterParrot = 0;
    private int counterTiger = 0;
    private int countTotalAttempts = 0;
    private int countNerve = 0;

    public ZooService (DAO dao, Session session) {
        this.dao = dao;
        this.session = session;
    }

    public List<Animal> allAnimal () {
        List<Animal> animalList = new ArrayList<>();

        animalList.add(new Animal(1, "Степан", 4, CategoryAnimal.PREDATOR, 8));
        animalList.add(new Animal(2, "Марта", 7, CategoryAnimal.HERBIVORE, 6));
        animalList.add(new Animal(3, "Гриша", 2, CategoryAnimal.OMNIVORE, 4));
        animalList.add(new Animal(4, "Ночка", 5, CategoryAnimal.PREDATOR, 5));
        animalList.add(new Animal(5, "Зоя", 12, CategoryAnimal.HERBIVORE, 15));
        animalList.add(new Animal(6, "Рекс", 3, CategoryAnimal.PREDATOR, 7));
        animalList.add(new Animal(7, "Феня", 1, CategoryAnimal.OMNIVORE, 2));
        animalList.add(new Animal(8, "Барсик", 6, CategoryAnimal.PREDATOR, 3));
        animalList.add(new Animal(9, "Хрюша", 4, CategoryAnimal.OMNIVORE, 5));
        animalList.add(new Animal(10, "Симба", 8, CategoryAnimal.PREDATOR, 9));
        animalList.add(new Animal(11, "Маня", 9, CategoryAnimal.HERBIVORE, 12));
        animalList.add(new Animal(12, "Гоша", 15, CategoryAnimal.HERBIVORE, 20));
        animalList.add(new Animal(13, "Лиза", 2, CategoryAnimal.PREDATOR, 4));
        animalList.add(new Animal(14, "Чарли", 5, CategoryAnimal.OMNIVORE, 6));

        return animalList;
    }


    public void functionHorse () {
        List<Employee> employeeList = dao.allEmployee();
        int randomEmployee = new Random().nextInt(0, employeeList.size() + 1);

        System.out.println("[Паймин подружайка]: Ура! Лошадки!");
        try {
            while (true) {
                System.out.println("[Паймин подружайка]: Что будем делать?");
                System.out.println("""
                        1. Погладить лошадку
                        2. Покормить лошадку (Платно 100 рублей)
                        3. Прочитать информацию про лошадку
                        4. Покинуть лошадку
                        """);

                System.out.print("[Паймин подружайка]: Выберите действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Введено отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println("[Паймин подружайка]: Оу, какая прелесть! Лошадка так мило меня облизывает!");
                        System.out.println("[Вьюга]: И-го-го!");
                    }

                    case 2 -> {
                        for (Employee employee : employeeList) {
                            if (employee.getId() == randomEmployee) {
                                if (counterHorse >= 5) {
                                    System.out.println("[" + employee.getName() + "]: Вы слишком часто кормите Вьюгу. Так нельзя делать!");
                                    break;
                                }
                            }
                        }

                        System.out.println("[Паймин подружайка]: Хм, надеюсь, что у тебя есть 100 рублей, чтобы мы могли покормить лошадку");

                        if (!(session.getUser().getBalance() > 100)) {
                            System.out.println("[Паймин подружайка]: У тебя не хватает денег, чтобы покормить лошадку(");
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Эх, значит в другой раз(");
                            continue;
                        }

                        if (dao.setNewBalanceUser(session.getUser(), session.getUser().getBalance() - 100)) {
                            session.getUser().setBalance(session.getUser().getBalance() - 100);
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Спасибо тебе, что ты покормил лошадку! Они будут тебя любить всегда!");
                            counterHorse++;
                        }
                    }

                    case 3 -> {
                        System.out.println("[Паймин подружайка]: О, это классная идея ознакомиться с лошадкой)");
                        Thread.sleep(2000);
                        System.out.println("[Табличка]: ");
                        System.out.println("Имя: Вьюга");
                        Thread.sleep(500);
                        System.out.println("Возраст: 10 лет");
                        Thread.sleep(500);
                        System.out.println("Особенность: Очень добрая и любимая. Также умеет прыгать в высоту 85 сантиметров");
                    }

                    case 4 -> {
                        return;
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void functionDogs () {
        List<Employee> employeeList = dao.allEmployee();
        int randomEmployee = new Random().nextInt(0, employeeList.size() + 1);

        System.out.println("[Паймин подружайка]: Ура! Собачки!");
        try {
            while (true) {
                System.out.println("[Паймин подружайка]: Что будем делать?");
                System.out.println("""
                        1. Погладить собачку
                        2. Покормить собачку (Платно 100 рублей)
                        3. Прочитать информацию про собачку
                        4. Покинуть собачку
                        """);

                System.out.print("[Паймин подружайка]: Выберите действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Введено отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println("[Паймин подружайка]: Оу, какая прелесть! Собачка так мило меня облизывает!");
                        System.out.println("[Тима]: Гав-гав!");
                    }

                    case 2 -> {
                        for (Employee employee : employeeList) {
                            if (employee.getId() == randomEmployee) {
                                if (counterDog >= 5) {
                                    System.out.println("[" + employee.getName() + "]: Вы слишком часто кормите Тиму. Так нельзя делать!");
                                    break;
                                }
                            }
                        }

                        System.out.println("[Паймин подружайка]: Хм, надеюсь, что у тебя есть 100 рублей, чтобы мы могли покормить собачку");

                        if (!(session.getUser().getBalance() > 100)) {
                            System.out.println("[Паймин подружайка]: У тебя не хватает денег, чтобы покормить собачку(");
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Эх, значит в другой раз(");
                            continue;
                        }

                        if (dao.setNewBalanceUser(session.getUser(), session.getUser().getBalance() - 100)) {
                            session.getUser().setBalance(session.getUser().getBalance() - 100);
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Спасибо тебе, что ты покормил собачку! Он будет тебя любить всегда!");
                            counterDog++;
                        }
                    }

                    case 3 -> {
                        System.out.println("[Паймин подружайка]: О, это классная идея ознакомиться с собачкой)");
                        Thread.sleep(2000);
                        System.out.println("[Табличка]: ");
                        System.out.println("Имя: Тима");
                        Thread.sleep(500);
                        System.out.println("Возраст: 5 лет");
                        Thread.sleep(500);
                        System.out.println("Особенность: Красивый, веселый, злой. Любит есть, спать, гулять");
                    }

                    case 4 -> {
                        return;
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void functionCats () {
        List<Employee> employeeList = dao.allEmployee();
        int randomEmployee = new Random().nextInt(0, employeeList.size() + 1);

        System.out.println("[Паймин подружайка]: Ура! Кошечка!");
        try {
            while (true) {
                System.out.println("[Паймин подружайка]: Что будем делать?");
                System.out.println("""
                        1. Погладить кошечку
                        2. Покормить кошечку (Платно 100 рублей)
                        3. Прочитать информацию про кошечку
                        4. Покинуть кошечку
                        """);

                System.out.print("[Паймин подружайка]: Выберите действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Введено отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println("[Паймин подружайка]: Оу, какая прелесть! Кошечка так мило меня облизывает!");
                        System.out.println("[Мася]: Мяу-мяу!");
                    }

                    case 2 -> {
                        for (Employee employee : employeeList) {
                            if (employee.getId() == randomEmployee) {
                                if (counterCat >= 5) {
                                    System.out.println("[" + employee.getName() + "]: Вы слишком часто кормите Масю. Так нельзя делать!");
                                    break;
                                }
                            }
                        }

                        System.out.println("[Паймин подружайка]: Хм, надеюсь, что у тебя есть 100 рублей, чтобы мы могли покормить кошечку");

                        if (!(session.getUser().getBalance() > 100)) {
                            System.out.println("[Паймин подружайка]: У тебя не хватает денег, чтобы покормить кошечку(");
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Эх, значит в другой раз(");
                            continue;
                        }

                        if (dao.setNewBalanceUser(session.getUser(), session.getUser().getBalance() - 100)) {
                            session.getUser().setBalance(session.getUser().getBalance() - 100);
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Спасибо тебе, что ты покормил кошечку! Они будут тебя любить всегда!");
                            counterCat++;
                        }
                    }

                    case 3 -> {
                        System.out.println("[Паймин подружайка]: О, это классная идея ознакомиться с кошечкой)");
                        Thread.sleep(2000);
                        System.out.println("[Табличка]: ");
                        System.out.println("Имя: Мася");
                        Thread.sleep(500);
                        System.out.println("Возраст: 7 лет");
                        Thread.sleep(500);
                        System.out.println("Особенность: Вредная и гуляет, где хочет");
                    }

                    case 4 -> {
                        return;
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void functionTiger () {
        List<Employee> employeeList = dao.allEmployee();
        int randomEmployee = new Random().nextInt(0, employeeList.size() + 1);

        System.out.println("[Паймин подружайка]: Ура! Мы пойдем к тигру!");
        try {
            while (true) {
                System.out.println("[Паймин подружайка]: Что будем делать?");
                System.out.println("""
                        1. Посмотреть на тигра
                        2. Покормить тигра (Платно 5000 рублей)
                        3. Прочитать информацию про тигра
                        4. Покинуть тигра
                        """);

                System.out.print("[Паймин подружайка]: Выберите действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Введено отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println("[Паймин подружайка]: Оу, какая прелесть! Тигр так мило себя ведет!");
                        System.out.println("[Степан]: Р-р-р!");
                    }

                    case 2 -> {
                        for (Employee employee : employeeList) {
                            if (employee.getId() == randomEmployee) {
                                if (counterTiger >= 5) {
                                    System.out.println("[" + employee.getName() + "]: Вы слишком часто кормите Степана. Так нельзя делать!");
                                    break;
                                }
                            }
                        }

                        System.out.println("[Паймин подружайка]: Хм, надеюсь, что у тебя есть 100 рублей, чтобы мы могли покормить тигра");

                        if (!(session.getUser().getBalance() > 5000)) {
                            System.out.println("[Паймин подружайка]: У тебя не хватает денег, чтобы покормить тигра(");
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Эх, значит в другой раз(");
                            continue;
                        }

                        if (dao.setNewBalanceUser(session.getUser(), session.getUser().getBalance() - 5000)) {
                            session.getUser().setBalance(session.getUser().getBalance() - 5000);
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Спасибо тебе, что ты покормил тигра! Он будет тебя любить всегда!");
                            counterTiger++;
                        }
                    }

                    case 3 -> {
                        System.out.println("[Паймин подружайка]: О, это классная идея ознакомиться с тигром)");
                        Thread.sleep(2000);
                        System.out.println("[Табличка]: ");
                        System.out.println("Имя: Степан");
                        Thread.sleep(500);
                        System.out.println("Возраст: Нет информации");
                        Thread.sleep(500);
                        System.out.println("Особенность: Не смотря на происхождение от суматранского и амурского тигров, Степан в основном амурский");
                    }

                    case 4 -> {
                        return;
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void functionParrot () {
        List<Employee> employeeList = dao.allEmployee();
        int randomEmployee = new Random().nextInt(0, employeeList.size() + 1);

        System.out.println("[Паймин подружайка]: Ура! Попугайчик!");
        try {
            while (true) {
                System.out.println("[Паймин подружайка]: Что будем делать?");
                System.out.println("""
                        1. Погладить попугая
                        2. Покормить попугая (Платно 100 рублей)
                        3. Прочитать информацию про попугая
                        4. Покинуть попугая
                        """);

                System.out.print("[Паймин подружайка]: Выберите действие: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Введено отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println("[Паймин подружайка]: Оу, какая прелесть! Попугай так мило летает!");
                        System.out.println("[Кеша]: Кра-кра!");
                    }

                    case 2 -> {
                        for (Employee employee : employeeList) {
                            if (employee.getId() == randomEmployee) {
                                if (counterParrot >= 5) {
                                    System.out.println("[" + employee.getName() + "]: Вы слишком часто кормите Кешу. Так нельзя делать!");
                                    break;
                                }
                            }
                        }

                        System.out.println("[Паймин подружайка]: Хм, надеюсь, что у тебя есть 100 рублей, чтобы мы могли покормить попугая");

                        if (!(session.getUser().getBalance() > 100)) {
                            System.out.println("[Паймин подружайка]: У тебя не хватает денег, чтобы покормить попугая(");
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Эх, значит в другой раз(");
                            continue;
                        }

                        if (dao.setNewBalanceUser(session.getUser(), session.getUser().getBalance() - 100)) {
                            session.getUser().setBalance(session.getUser().getBalance() - 100);
                            Thread.sleep(1000);
                            System.out.println("[Паймин подружайка]: Спасибо тебе, что ты покормил попугая! Он будет тебя любить всегда!");
                            counterParrot++;
                        }
                    }

                    case 3 -> {
                        System.out.println("[Паймин подружайка]: О, это классная идея ознакомиться с попугаем)");
                        Thread.sleep(2000);
                        System.out.println("[Табличка]: ");
                        System.out.println("Имя: Кеша");
                        Thread.sleep(500);
                        System.out.println("Возраст: 5 лет");
                        Thread.sleep(500);
                        System.out.println("Особенность: Звонкий, очень общительный");
                    }

                    case 4 -> {
                        return;
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void stealMachine () {
        try {
            Thread.sleep(2000);
            System.out.println("[Фуфишмерт Злодей]: Отлично. Тогда нам нужно украсть автомат с едой.");
            Thread.sleep(1000);
            System.out.println("[Фуфишмерт Злодей]: Для этого тебе надо решить задач, после чего тебе будет доступна возможность снять его.");
            Thread.sleep(1000);
            System.out.println("[Фуфишмерт Злодей]: Я пойду отвлеку сотрудников, а ты давай забирай его");

            while (true) {
                System.out.println("[CONSOLE]: Что вы хотите сделать: ");
                System.out.println("""
                        1. Пополнить вкусняшки
                        2. Снять автомат
                        3. Забрать деньги
                        """);

                System.out.print("[CONSOLE]: Ваш выбор: ");
                int choice = Integer.parseInt(CONSOLE.nextLine().trim());

                if (choice <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное число или равное нулю!");
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.println("[CONSOLE]: Необходимо вставить ключ доступа. Пожалуйста, вставьте ключ доступа.");
                        System.out.println();
                    }

                    case 2 -> {
                        System.out.println("[CONSOLE]: Решите несколько задач.");
                        System.out.println("[CONSOLE]: Задачка №1");
                        System.out.print("[CONSOLE]: 9 * 4 = ");
                        int answer1 = Integer.parseInt(CONSOLE.nextLine().trim());

                        if (countTotalAttempts != 4) {
                            if (answer1 == 36) {
                                System.out.println("[CONSOLE]: Задачка №2");
                                System.out.print("[CONSOLE]: 4 * 2 + 3 = ");
                                int answer2 = Integer.parseInt(CONSOLE.nextLine().trim());

                                if (countTotalAttempts == 4) {
                                    System.out.println("[CONSOLE]: Вы были задержаны Перри-Утканосом! Было допущено слишком много неудачных попыток!");
                                    return;
                                }

                                if (answer2 == 11) {
                                    System.out.println("[CONSOLE]: Задачка №3");
                                    System.out.print("[CONSOLE]: 7 * 9 - 6 + 1 = ");
                                    int answer3 = Integer.parseInt(CONSOLE.nextLine().trim());

                                    if (countTotalAttempts == 4) {
                                        System.out.println("[CONSOLE]: Вы были задержаны Перри-Утканосом! Было допущено слишком много неудачных попыток!");
                                        return;
                                    }

                                    if (answer3 == 58) {
                                        System.out.println("[CONSOLE]: Задачка №4");
                                        System.out.print("[CONSOLE]: 1 * 1 - 1 + 2 = ");
                                        int answer4 = Integer.parseInt(CONSOLE.nextLine().trim());

                                        if (countTotalAttempts == 4) {
                                            System.out.println("[CONSOLE]: Вы были задержаны Перри-Утканосом! Было допущено слишком много неудачных попыток!");
                                            return;
                                        }

                                        if (answer4 == 2) {
                                            System.out.println("[CONSOLE]: Автомат успешно разблокирован для снятия!");
                                            return;
                                        }

                                        else {
                                            System.out.println("[CONSOLE]: Неверный ответ!");
                                            countTotalAttempts++;
                                        }
                                    }

                                    else {
                                        System.out.println("[CONSOLE]: Неверный ответ!");
                                        countTotalAttempts++;
                                    }
                                }

                                else {
                                    System.out.println("[CONSOLE]: Неверный ответ!");
                                    countTotalAttempts++;
                                }
                            }

                            else {
                                System.out.println("[CONSOLE]: Неверный ответ!");
                                countTotalAttempts++;
                            }
                        }

                        else {
                            System.out.println("[CONSOLE]: Вы были задержаны Перри-Утканосом! Было допущено слишком много неудачных попыток!");
                            return;
                        }
                    }

                    case 3 -> {
                        System.out.println("[CONSOLE]: Необходим ключ доступа!");
                        System.out.println();
                    }

                    default -> {
                        System.out.println("[CONSOLE]: Неверный ответ!");
                        System.out.println();
                    }
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void createFakeNamePlate () {
        try {
            System.out.println("[Фуфишмерт Злодей]: Ох какой прекрасный план!");
            Thread.sleep(1000);
            System.out.println("[Фуфишмерт Злодей]: Для его реализации необходимо лишь твоя фантазия. Удачи!");
            Thread.sleep(1500);
            System.out.println("[Фуфишмерт Злодей]: А я пока пойду создам ловушку для Перри-Утканоса!");

            while (true) {
                System.out.print("[CONSOLE]: Введите имя животного: ");
                String name = CONSOLE.nextLine().trim();

                if (name.isBlank()) {
                    System.out.println("[System]: Вы ввели пустую строку!");
                    continue;
                }

                if (name.length() > 20) {
                    System.out.println("[System]: Имя животного должно быть меньше 20 символов!");
                    continue;
                }

                System.out.println("[CONSOLE]: Имя животного: '" + name + "'");
                Thread.sleep(1000);
                System.out.print("[CONSOLE]: Введите возраст животного: ");
                int age = Integer.parseInt(CONSOLE.nextLine().trim());

                if (age <= 0) {
                    System.out.println("[System]: Вы ввели отрицательное число или равное нулю!");
                    continue;
                }

                System.out.println("[CONSOLE]: Возраст животного: " + age);
                Thread.sleep(1000);
                System.out.print("[CONSOLE]: Введите краткое описание животного или опишите его особенность, черту характера или что-то другое: ");
                String about = CONSOLE.nextLine().trim();

                if (about.isBlank()) {
                    System.out.println("[System]: Вы не ввели описание животного!");
                    continue;
                }

                if (about.length() > 150) {
                    System.out.println("[System]: Описание слишком длинное!");
                    continue;
                }

                System.out.println("[CONSOLE]: Описание: " + about);
                Thread.sleep(500);
                System.out.println("[CONSOLE]: Обновление. Подождите немного!");
                Thread.sleep(3000);
                System.out.println("[CONSOLE]: Обновление завершено!");
                Thread.sleep(500);
                System.out.println("[CONSOLE]: Табличка создана!");
                System.out.println("[Табличка]: ");
                System.out.println("Имя: " + name);
                System.out.println("Возраст: " + age);
                System.out.println("Описание: " + about);
                System.out.println();

                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/game/info" + name + ".txt"))) {
                    bufferedWriter.write("Имя животного: " + name);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Возраст: " + age);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Описание: " + about);

                    System.out.println("[CONSOLE]: Файл успешно создан!");
                    break;
                }

                catch (IOException e) {
                    GlobalExceptionHandler.handle(19, e);
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void feedAllAnimals () {
        try {
            List<Animal> animalList = allAnimal();

            if (animalList.size() > 10) {
                System.out.println("[Жибер Начальик]: Ты можешь попросить коллег тебе помочь.");
                Thread.sleep(1000);

                while (true) {
                    System.out.print("[Жибер Начальик]: Будешь просить помощи (Да/Нет): ");
                    String answer = CONSOLE.nextLine().trim();

                    if (answer.isBlank()) {
                        System.out.println("[System]: Вы не ввели значение!");
                        return;
                    }

                    if (answer.equalsIgnoreCase("Да")) {
                        System.out.println("[Жибер Начальик]: Отлично, поищи коллег и попроси помощи.");
                        break;
                    }

                    else if (answer.equalsIgnoreCase("Нет")) {
                        System.out.println("[Жибер Начальик]: Тогда ты будешь делать это один. Удачи.");

                        for (Animal animal : animalList) {

                            if (countNerve == 5) {
                                System.out.println("[Жибер Начальик]: Пошел вон. Ты уволен!");
                                System.out.println("[CONSOLE]: Game over!");
                                System.exit(0);
                            }

                            System.out.println("[Жибер Начальик]: Необходимо покормить " + animal.getName());
                            Thread.sleep(1000);
                            System.out.println("[Жибер Начальик]: Укажи необходимое количество еды для " + animal.getName());
                            Thread.sleep(1000);
                            System.out.println("[Жибер Начальик]: Даю подсказку, необходимо дать (" + animal.getFood() +" кг)");
                            System.out.print("[Жибер Начальик]: Введи количество еды: ");
                            int food = Integer.parseInt(CONSOLE.nextLine().trim());

                            if (food == animal.getFood()) {
                                System.out.println("[Жибер Начальик]: Молодец! Продолжаем выполнять наши задачи!");
                            }

                            else {
                                System.out.println("[Жибер Начальик]: Ошибся с количеством еды. Начинаем сначала.");
                                countNerve++;
                                break;
                            }
                        }
                    }

                    else {
                        System.out.println("[Жибер Начальик]: Я не понял твой ответ. Дай четкий ответ.");
                        continue;
                    }
                }

                List<Employee> employeeList = dao.allEmployee();
                int randomCountAnimal = new Random().nextInt(1, animalList.size() + 1);
                int randomEmployee = new Random().nextInt(1, employeeList.size() + 1);

                for (Employee employee : employeeList) {
                    if (employee.getId() == randomEmployee) {
                        System.out.println("[" + session.getUser().getName() + "]: Эй, " + employee.getName() + ", поможешь мне?");
                        Thread.sleep(1000);
                        System.out.println("[" + employee.getName() + "]: Конечно, " + session.getUser().getName() + ". Что нужно сделать?");
                        Thread.sleep(1000);
                        System.out.println("[" + session.getUser().getName() + "]: Надо покормить всех животных.");
                        Thread.sleep(2000);
                        System.out.println("[" + employee.getName() + "]: Оу, это тяжело, но я помогу.");
                        Thread.sleep(500);
                        System.out.println("[" + employee.getName() + "]: Я возьму следующих животных на себя: ");

                        for (int i = 0; i < randomCountAnimal; i++) {
                            for (Animal animal : animalList) {
                                int randomAnimal = new Random().nextInt(1, animalList.size() + 1);

                                if (animal.getId() == randomAnimal) {
                                    System.out.println("[" + employee.getName() + "]: " + animal.getName());
                                }
                            }
                        }

                        Thread.sleep(1300);
                        System.out.println();
                        System.out.println("[" + session.getUser().getName() + "]: Спасибо, ты легенда!");
                        Thread.sleep(400);
                        System.out.println("[" + employee.getName() + "]: Не за что.");
                        Thread.sleep(4000);
                        System.out.println("[Жибер Начальик]: Отличная работа парни!");
                        return;
                    }
                }
            }

            System.out.println("[Жибер Начальик]: Не так уж и много животных надо покормить, я думаю, что ты можешь справиться сам.");

            for (Animal animal : animalList) {

                if (countNerve == 5) {
                    System.out.println("[Жибер Начальик]: Пошел вон. Ты уволен!");
                    System.out.println("[CONSOLE]: Game over!");
                    System.exit(0);
                }

                System.out.println("[Жибер Начальик]: Необходимо покормить " + animal.getName());
                Thread.sleep(1000);
                System.out.println("[Жибер Начальик]: Укажи необходимое количество еды для " + animal.getName());
                Thread.sleep(1000);
                System.out.println("[Жибер Начальик]: Даю подсказку, необходимо дать (" + animal.getFood() +" кг)");
                System.out.print("[Жибер Начальик]: Введи количество еды: ");
                int food = Integer.parseInt(CONSOLE.nextLine().trim());

                if (food == animal.getFood()) {
                    System.out.println("[Жибер Начальик]: Молодец! Продолжаем выполнять наши задачи!");
                }

                else {
                    System.out.println("[Жибер Начальик]: Ошибся с количеством еды. Начинаем сначала.");
                    countNerve++;
                    break;
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void sellTickets () {
        try {
            List<User> userList = dao.allUsers();

            System.out.println("[Жибер Начальик]: Отлично. Желаю удачи! ");
            Thread.sleep(1000);
            System.out.println("[Жибер Начальик]: Да, чуть не забыл, 1 билет стоит 350 рублей.");
            Thread.sleep(800);
            System.out.println("[" + session.getUser().getName() + "]: Понял. Все сделаю в лучше виде!");
            Thread.sleep(3000);

            int randomCountTicket = new Random().nextInt(1, userList.size() + 1);
            int random = new Random().nextInt(1, 9);

            System.out.println();

            for (int i = 0; i < random; i++) {
                for (User user : userList) {
                    int randomUser = new Random().nextInt(1, userList.size() + 1);

                    if (user.getId() == randomUser) {
                        System.out.println("[" + user.getName() + "]: Здравствуйте. Мне нужно " + randomCountTicket + " билетов.");
                        Thread.sleep(2000);
                        System.out.println("[" + session.getUser().getName() + "]: С вас " + (randomCountTicket * 350) + " рублей.");

                        if (user.getBalance() >= randomCountTicket * 350) {
                            Thread.sleep(1500);
                            System.out.println("[" + user.getName() + "]: Оплатил.");
                            Thread.sleep(500);
                            System.out.println("[" + session.getUser().getName() + "]: Спасибо! Приходите еще!");
                        }

                        else {
                            Thread.sleep(1000);
                            System.out.println("[" + session.getUser().getName() + "]: У вас недостаточно средств.");
                            Thread.sleep(500);
                            System.out.println("[" + user.getName() + "]: О, я забыл. Позже приду(");
                        }
                    }
                    System.out.println();
                }
            }
        }

        catch (Exception e) {
            System.out.println("[System]: Ошибка: " + e.getMessage());
        }
    }

    public void sortAnimalsByName () {
        List<Animal> animalList = allAnimal();
        List<Animal> newAnimalList = animalList.stream()
                .sorted(Comparator.comparing(Animal::getName))
                .toList();

        System.out.println("[" + session.getUser().getName() + "]: Сортируем животных по алфавиту...");
        try {
            Thread.sleep(3000);
            for (Animal animal : newAnimalList) {
                Thread.sleep(500);
                System.out.println(animal.toString());
                System.out.println();
            }
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }
}