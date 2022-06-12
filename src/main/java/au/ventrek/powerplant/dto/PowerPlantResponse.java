package au.ventrek.powerplant.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonPropertyOrder({"message", "statusCode", "data"})
@Data
public class PowerPlantResponse {

    private String message;
    private int statusCode;
    private Object data;

    public PowerPlantResponse(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public PowerPlantResponse(int statusCode, String message, Object data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }


    public PowerPlantResponse(HttpStatus status, Object data) {
        this.message = status.getReasonPhrase();
        this.statusCode = status.value();
        this.data = data;
    }

    public PowerPlantResponse(HttpStatus status) {
        this.message = status.getReasonPhrase();
        this.statusCode = status.value();
    }

}
