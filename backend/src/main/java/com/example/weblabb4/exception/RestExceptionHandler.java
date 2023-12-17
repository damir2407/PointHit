package com.example.weblabb4.exception;

import com.example.weblabb4.dto.response.ErrorResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());

        List<FieldError> fields = e.getBindingResult().getFieldErrors();

        return new ErrorResponse(
            "Неправильно введенны поля! " + fields.stream().map(FieldError::getField).collect(Collectors.joining(", ")),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage());

        return new ErrorResponse(
            "Неправильно введенны поля! " + e.getConstraintViolations().stream()
                .map(violation -> ((PathImpl) violation.getPropertyPath()).getLeafNode().getName())
                .collect(Collectors.joining(", ")),
            LocalDateTime.now()
        );
    }

    @ExceptionHandler({UserAlreadyExistException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(Exception e) {
        log.error(e.getMessage());

        return new ErrorResponse(
            e.getMessage(),
            LocalDateTime.now()
        );
    }
}
