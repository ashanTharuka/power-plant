package au.ventrek.powerplant.exception;

import au.ventrek.powerplant.dto.PowerPlantResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<PowerPlantResponse> handleGlobalException(Exception exception) {
		LOGGER.error(" handleGlobalException error : ",exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
				body(new PowerPlantResponse(HttpStatus.INTERNAL_SERVER_ERROR));
	}
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<PowerPlantResponse> handleMediaTypeException(HttpMediaTypeNotSupportedException exception) {
		LOGGER.error(" handleMediaTypeException error : ",exception);
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).
				body(new PowerPlantResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE));
	}

	@ExceptionHandler(DCBException.class)
	 ResponseEntity<PowerPlantResponse> handleGlobalException(DCBException e) {
		return ResponseEntity.status(e.getErrorCode()).body(new PowerPlantResponse(e.getErrorCode(), e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<PowerPlantResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, List<String>> errorBody=new HashMap<>();
		List<String>errors=ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		errorBody.put("errors",errors);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				new PowerPlantResponse(HttpStatus.BAD_REQUEST,errorBody));
	}
}
