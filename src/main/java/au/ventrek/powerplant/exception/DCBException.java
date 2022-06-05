package au.ventrek.powerplant.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class DCBException extends Exception{
    private final int errorCode;
    private final String message;

    public DCBException(HttpStatus status) {
        this.errorCode = status.value();
        this.message = status.getReasonPhrase();
    }
}
