package pl.medos.cmmsApi.exception;

public class ExcelFileNotFoundException extends Exception{
    public ExcelFileNotFoundException() {
    }

    public ExcelFileNotFoundException(String message) {
        super(message);
    }

    public ExcelFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExcelFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
