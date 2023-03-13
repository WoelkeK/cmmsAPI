package pl.medos.cmmsApi.exception;

public class CostNotFoundException extends Exception{

    public CostNotFoundException() {
    }

    public CostNotFoundException(String message) {
        super(message);
    }

    public CostNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CostNotFoundException(Throwable cause) {
        super(cause);
    }

    public CostNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
