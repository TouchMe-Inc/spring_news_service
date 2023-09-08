package by.touchme.newsservice.handler;

import by.touchme.newsservice.exception.NewsNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller that catches all thrown exceptions.
 */
@RestControllerAdvice
public class ErrorHandler {

    /**
     * The method expects a NotFound error and generates a response for the request.
     *
     * @param ex NewsNotFoundException
     * @return Object with error message
     */
    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<Object>  handleNotFoundException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AccessDeniedException.class, ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    public ResponseEntity<Object> handleMalformedJwtException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Any unhandled exceptions.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        System.out.println(ex.getClass());
        return prepareErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> prepareErrorMessage(Exception ex, WebRequest request, HttpStatus status) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), requestUri);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), status);
    }
}
