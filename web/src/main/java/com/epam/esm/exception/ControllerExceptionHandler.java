package com.epam.esm.exception;

import com.epam.esm.exception.certificate.CertificateNameAlreadyExistsException;
import com.epam.esm.exception.certificate.CertificateNotFoundException;
import com.epam.esm.exception.certificate.UnsupportedSearchParamNameCertificateException;
import com.epam.esm.exception.certificate.UnsupportedSortedParamNameCertificateException;
import com.epam.esm.exception.order.OrderByUserNotFoundException;
import com.epam.esm.exception.order.OrderNotFoundException;
import com.epam.esm.exception.tag.TagNameAlreadyExistsException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final static String ERROR_MESSAGE = "errorMessage";
    private final static String ERROR_CODE = "errorCode";

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private ResponseEntity<Object> getResponseEntity(String errorMessage,
                                                     int errorCode,
                                                     HttpStatus httpStatus) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(ERROR_MESSAGE, errorMessage);
        parameters.put(ERROR_CODE, errorCode);
        return new ResponseEntity<>(parameters, httpStatus);
    }

    @ExceptionHandler({TagNameAlreadyExistsException.class})
    public ResponseEntity<Object> handleException(TagNameAlreadyExistsException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40001", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40001, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TagNotFoundException.class})
    public ResponseEntity<Object> handleException(TagNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40401", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40401, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CertificateNameAlreadyExistsException.class})
    public ResponseEntity<Object> handleException(CertificateNameAlreadyExistsException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40005", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40005, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CertificateNotFoundException.class})
    public ResponseEntity<Object> handleException(CertificateNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40402", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40402, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UnsupportedSearchParamNameCertificateException.class})
    public ResponseEntity<Object> handleException(UnsupportedSearchParamNameCertificateException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40006", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40006, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnsupportedSortedParamNameCertificateException.class})
    public ResponseEntity<Object> handleException(UnsupportedSortedParamNameCertificateException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40007", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40007, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleUnregisterException(ConstraintViolationException exception,
                                                            Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.40010", null, locale);
        return getResponseEntity(messageBundle, 40010, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleUnregisterException(MethodArgumentTypeMismatchException exception,
                                                            Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.40011", null, locale);
        return getResponseEntity(messageBundle, 40011, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumPageNotExistException.class)
    public ResponseEntity<Object> handleUnregisterException(NumPageNotExistException exception,
                                                            Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40012", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40012, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleUnregisterException(MethodArgumentNotValidException exception,
                                                            Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.40013", null, locale);
        return getResponseEntity(messageBundle, 40013, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderByUserNotFoundException.class)
    public ResponseEntity<Object> handleUnregisterException(OrderByUserNotFoundException exception,
                                                            Locale locale) {
        int userId = exception.getUserId();
        int orderId = exception.getOrderId();
        String messageBundle =
                messageSource.getMessage("exception.message.40410", null, locale);
        return getResponseEntity(String.format(messageBundle, orderId, userId),
                40410, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleUnregisterException(HttpRequestMethodNotSupportedException exception,
                                                            Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.40400", null, locale);
        return getResponseEntity(messageBundle, 40400, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleException(UserNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40403", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40403, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({OrderNotFoundException.class})
    public ResponseEntity<Object> handleException(OrderNotFoundException exception,
                                                  Locale locale) {
        String exceptionMessage = exception.getMessage();
        String messageBundle =
                messageSource.getMessage("exception.message.40404", null, locale);
        return getResponseEntity(String.format(messageBundle, exceptionMessage),
                40404, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleUnregisterException(Locale locale) {
        String messageBundle =
                messageSource.getMessage("exception.message.50001", null, locale);
        return getResponseEntity(messageBundle, 50001, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}