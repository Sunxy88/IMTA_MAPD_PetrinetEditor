package xin.xisx.petrinet.common.exception;

public class IllegalWeightException extends RuntimeException {
    public IllegalWeightException() {
        super("Weight should be positive");
    }
}
