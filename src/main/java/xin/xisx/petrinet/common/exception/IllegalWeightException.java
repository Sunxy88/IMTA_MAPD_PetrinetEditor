package xin.xisx.petrinet.common.exception;

/**
 * @author Xi Song
 */
public class IllegalWeightException extends RuntimeException {
    public IllegalWeightException() {
        super("Weight should be positive");
    }
}
