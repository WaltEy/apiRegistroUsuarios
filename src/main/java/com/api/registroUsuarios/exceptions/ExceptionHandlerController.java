package com.api.registroUsuarios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(FindedEmailException.class)
    public ResponseEntity<ErrorJson> handleFindedEmailException(FindedEmailException ex) {
        ErrorJson error = new ErrorJson(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordFormatException.class)
    public ResponseEntity<ErrorJson> handlePasswordFormatException(PasswordFormatException ex) {
        ErrorJson error = new ErrorJson(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailFormatException.class)
    public ResponseEntity<ErrorJson> handleEmailFormatException(EmailFormatException ex) {
        ErrorJson error = new ErrorJson(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    public static class ErrorJson {
        private String message;

        public ErrorJson(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
