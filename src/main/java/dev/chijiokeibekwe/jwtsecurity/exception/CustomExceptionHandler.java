package dev.chijiokeibekwe.jwtsecurity.exception;

import dev.chijiokeibekwe.jwtsecurity.common.ResponseObject;
import dev.chijiokeibekwe.jwtsecurity.enums.ResponseStatus;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ResponseObject<?>> handleAuthenticationException(AuthenticationException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                "Token is missing or invalid",
                null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseObject<?>> handleAccessDeniedException(AccessDeniedException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                "You are not authorized to perform this action",
                null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseObject<?>> handleBadCredentialsException(BadCredentialsException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                "Invalid username or password",
                null
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<?>> handleInvalidMethodArgumentException(MethodArgumentNotValidException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseObject<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ResponseObject<?>> handleEntityNotFoundException(EntityNotFoundException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<ResponseObject<?>> handleEntityExistsException(EntityExistsException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseObject<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseObject<?>> handleConstraintViolationException(ConstraintViolationException e) {

        List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(e.getConstraintViolations());

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                constraintViolations.get(0).getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler({TransactionSystemException.class})
    public ResponseEntity<ResponseObject<?>> handleTransactionSystemException(TransactionSystemException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        if (e.getRootCause() instanceof ConstraintViolationException constraintViolationException) {
            List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(constraintViolationException.getConstraintViolations());

            response =  new ResponseObject<>(
                    ResponseStatus.FAILED,
                    constraintViolations.get(0).getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseObject<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ResponseObject<?>> handleNoResourceFoundException(NoResourceFoundException e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                e.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseObject<?>> handleGenericException(Exception e) {

        ResponseObject<?> response =  new ResponseObject<>(
                ResponseStatus.FAILED,
                "An error occurred while processing your request. Please try again",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
