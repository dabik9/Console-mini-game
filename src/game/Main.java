package game;

import game.dao.DAO;
import game.exceptions.GlobalExceptionHandler;
import game.location.LocationStart;
import game.model.User;
import game.service.LoginService;
import game.service.Session;

import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main (String []args) {
        Session session = new Session();
        Main main = new Main();
        main.startGem();
    }

    public void startGem() {
        Scanner CONSOLE = new Scanner(System.in);
        DAO dao = new DAO();
        Session session = new Session();
        LoginService serviceLogin = new LoginService(dao, session);

        String uuid = UUID.randomUUID().toString();

        System.out.println(uuid);
        System.out.println("[GAME]: Start Game!");
        System.out.println("[CONSOLE]: Hello player!");
        System.out.println("[CONSOLE]: You need to log in to your account to start playing. The account details will be given to you by 'Даня<3'.");

        System.out.print("[CONSOLE]: Enter your login: ");
        String username = CONSOLE.nextLine();
        System.out.print("[CONSOLE]: Enter your password: ");
        String password = CONSOLE.nextLine();
        User user = serviceLogin.login(username, password);

        if (user == null) {
            System.out.println("[CONSOLE]: Invalid login or password.");
            return;
        }

        System.out.println("[CONSOLE]: Welcome, " + user.getName() + "!");

        try {
            Thread.sleep(5000);
            System.out.println("[CONSOLE]: Error 156!");
            Thread.sleep(13000);
            System.out.println("[CONSOLE]: 3!");
            Thread.sleep(2000);
            System.out.println("[CONSOLE]: 2!");
            Thread.sleep(2000);
            System.out.println("[CONSOLE]: 1!");
            Thread.sleep(2000);
            System.out.println("[CONSOLE]: 0!");
            Thread.sleep(2000);
            System.out.println("[CONSOLE]: -1!");
            Thread.sleep(2000);
            System.out.println("[CONSOLE]: -2!");
            Thread.sleep(2000);
            System.out.println("[CONSOLE]: -3!");
            Thread.sleep(5000);
            System.out.println();
            System.out.println("[CONSOLE]: Good Luck!");
            System.out.println();

            LocationStart locationStart = new LocationStart(dao, session);
            locationStart.start();
        }

        catch (InterruptedException e) {
            GlobalExceptionHandler.handle(83, e);
        }
    }
}