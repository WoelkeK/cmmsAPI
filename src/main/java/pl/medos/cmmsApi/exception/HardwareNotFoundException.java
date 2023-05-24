package pl.medos.cmmsApi.exception;

public class HardwareNotFoundException extends Exception{

    public HardwareNotFoundException() {
    }

    public HardwareNotFoundException(String message) {
        super(message);
    }

    public HardwareNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HardwareNotFoundException(Throwable cause) {
        super(cause);
    }

    public HardwareNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
