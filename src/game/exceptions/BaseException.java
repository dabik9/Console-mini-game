package game.exceptions;

public class BaseException extends RuntimeException {
    private int codError;
    public BaseException(int codeError, String message) {
        super(message);
        this.codError = codeError;
    }

    public int getCodError() {
        return codError;
    }
}