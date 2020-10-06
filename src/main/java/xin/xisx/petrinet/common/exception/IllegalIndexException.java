package xin.xisx.petrinet.common.exception;

public class IllegalIndexException extends RuntimeException {

    public IllegalIndexException(String message) {
        super(message);
    }

    public IllegalIndexException(Integer bound) {
        this("Index should be between" + 0 + " and " + bound);
    }
}
