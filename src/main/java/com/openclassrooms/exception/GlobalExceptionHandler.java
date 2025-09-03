package com.openclassrooms.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.EntityNotFoundException;

/**
 * Handles all exceptions in the application and returns proper error messages
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles when a resource is not found in the database
   *
   * @param e the exception thrown when entity is not found
   * @return error message map
   */
  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleEntityNotFound(EntityNotFoundException e) {
    return Map.of("Error", "Ressource non trouvée");
  }

  /**
   * Handles when an ID has wrong number format
   *
   * @param e the exception thrown when number format is wrong
   * @return error message map
   */
  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleNumberFormatException(NumberFormatException e) {
    return Map.of("Error", "Format d'id non valide");
  }

  /**
   * Handles when arguments are not valid
   *
   * @param e the exception thrown when arguments are wrong
   * @return error message map
   */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleIllegalArgumentException(IllegalArgumentException e) {
    return Map.of("Error", e.getMessage());
  }

  /**
   * Handles HTTP status exceptions
   *
   * @param ex the response status exception
   * @return error response with proper HTTP status
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> handleResponseStatus(ResponseStatusException ex) {
    Map<String, String> error = Map.of("message", ex.getReason());
    return new ResponseEntity<>(error, ex.getStatusCode());
  }

  /**
   * Handles form validation errors
   *
   * @param ex the validation exception with field errors
   * @return map of field names and error messages
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}
