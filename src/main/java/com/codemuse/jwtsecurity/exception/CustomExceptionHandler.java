package com.codemuse.jwtsecurity.exception;

import com.codemuse.jwtsecurity.common.ResponseObject;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = JwtException.class)
    public ResponseEntity<ResponseObject<?>> handleJwtException(JwtException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response =  ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ResponseObject<?>> handleAccessDeniedException(AccessDeniedException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response =  ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message("You are not authorized to perform this action")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseObject<?>> handleBadCredentialsException(BadCredentialsException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response =  ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message("Invalid username or password")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ResponseObject<?>> handleEntityNotFoundException(EntityNotFoundException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<ResponseObject<?>> handleEntityExistsException(EntityExistsException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseObject<?>> handleConstraintViolationException(ConstraintViolationException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject<?>> handleInvalidMethodArgumentException(MethodArgumentNotValidException e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message(e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseObject<?>> handleGenericException(Exception e) {

        log.error(e.getMessage(), e);

        ResponseObject<?> response = ResponseObject.builder()
                .status(ResponseObject.ResponseStatus.FAILED)
                .message("An error occurred while processing your request. Please try again")
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
