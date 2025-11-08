package game.service;

import game.dao.DAO;
import game.exceptions.BaseException;
import game.exceptions.GlobalExceptionHandler;
import game.model.User;

import java.util.Scanner;

public class LoginService {
    private final Scanner CONSOLE = new Scanner(System.in);
    private final DAO userDAO;
    private final Session session;

    public LoginService (DAO userDAO, Session session) {
        this.userDAO = userDAO;
        this.session = session;
    }

    public User login (String username, String password) {
        if (username.isBlank() || password.isBlank()) {
            return null;
        }

        User user = userDAO.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        session.setUser(user);
        return user;
    }
}