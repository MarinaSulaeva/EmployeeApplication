package ru.skypro.lessons.springboot.weblibrary_1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleIOException(IllegalArgumentException illegalArgumentException) {
        String message = "Введен некорректный ID";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleSQLException(SQLException sqlException) {
        String message = "Во время выполнения запроса произошла ошибка на сервере";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception exception) {
        String message = "URL неверный или такого действия нет в веб-приложении";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
