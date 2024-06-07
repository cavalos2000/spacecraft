package worktomeet.travel.spacecraft.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SpacecraftNotFoundException.class)
    public ResponseEntity<?> handleSpacecraftNotFoundException(SpacecraftNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = ErrorDetails.builder().
                statusCode(HttpStatus.NOT_FOUND.value()).
                message(ex.getMessage()).
                details(request.getDescription(false)).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =
                ErrorDetails.builder().
                        statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                        message(ex.getMessage()).
                        details(request.getDescription(false)).build();
        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Getter
@Setter
@Builder
class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;
}

