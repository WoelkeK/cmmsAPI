package pl.medos.cmmsApi.exception;

public class SoftwareNotFoundException extends Exception{
    public SoftwareNotFoundException() {
    }

    public SoftwareNotFoundException(String message) {
        super(message);
    }

    public SoftwareNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoftwareNotFoundException(Throwable cause) {
        super(cause);
    }

    public SoftwareNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
