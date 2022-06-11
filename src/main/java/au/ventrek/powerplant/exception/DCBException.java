package au.ventrek.powerplant.exception;

import lombok.Data;

@Data
public class DCBException extends Exception{
    private final int errorCode;
    private final String message;

    public DCBException(DCBError error) {
        this.errorCode = error.getCode();
        this.message = error.getDescription();
    }
}
