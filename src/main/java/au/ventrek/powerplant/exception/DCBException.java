package au.ventrek.powerplant.exception;

import lombok.Data;

@Data
public class DCBException extends Exception {
    private final int errorCode;
    private final String message;
    private final Exception error;

    public DCBException(DCBError errorMessage, Exception error) {
        this.errorCode = errorMessage.getCode();
        this.message = errorMessage.getDescription();
        this.error = error;
    }

}
