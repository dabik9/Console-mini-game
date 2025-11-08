package game.exceptions;

public class GlobalExceptionHandler {
    public static void handle (BaseException e) {
        System.out.println("[System]: Код ошибки: " + e.getCodError() + ". Ошибка: " + e.getMessage());
    }

    public static void handle(int codError, Exception e) {
        System.out.println("[System]: Код ошибки: " + codError + ". Ошибка: " + e.getMessage());
    }
}