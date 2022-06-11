package au.ventrek.powerplant.exception;

import lombok.Getter;

@Getter
public enum DCBError {
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409,"Conflict"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type");

    private int code;
    private String description;

    DCBError(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
