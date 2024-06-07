package worktomeet.travel.spacecraft.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleSpacecraftNotFoundException_shouldReturnNotFoundResponse() {
        SpacecraftNotFoundException exception = new SpacecraftNotFoundException("Spacecraft not found");
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/spacecraft/1");

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleSpacecraftNotFoundException(exception, webRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), errorDetails.getStatusCode());
        assertEquals("Spacecraft not found", errorDetails.getMessage());
        assertEquals("uri=/spacecraft/1", errorDetails.getDetails());
    }

    @Test
    void handleGlobalException_shouldReturnInternalServerErrorResponse() {
        Exception exception = new Exception("Internal server error");
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/error");

        ResponseEntity<?> responseEntity = globalExceptionHandler.handleGlobalException(exception, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorDetails.getStatusCode());
        assertEquals("Internal server error", errorDetails.getMessage());
        assertEquals("uri=/error", errorDetails.getDetails());
    }
}
