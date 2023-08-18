package by.touchme.newsservice.controller;

import by.touchme.newsservice.exception.CommentNotFoundException;
import by.touchme.newsservice.exception.NewsNotFoundException;
import by.touchme.newsservice.record.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler({NewsNotFoundException.class, CommentNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundException(Exception e) {
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage anyException(Exception e) {
        logger.error("Unexpected error", e);
        return new ErrorMessage(e.getMessage());
    }
}
