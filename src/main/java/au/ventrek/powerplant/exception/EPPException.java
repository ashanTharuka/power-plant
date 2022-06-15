package au.ventrek.powerplant.exception;

import lombok.Data;

@Data
public class EPPException extends Exception {
    private final int errorCode;
    private final String message;


    public EPPException(DCBError error) {
        this.errorCode = error.getCode();
        this.message = error.getDescription();
    }
}
