package com.chuan.taskmanagement.exception;

import com.chuan.taskmanagement.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ServiceAppExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    private String getI18nMessage(String code, String fallbackCode, WebRequest request, Object... args) {
        try {
            return messageSource.getMessage(code, args, request.getLocale());
        } catch (NoSuchMessageException nsme) {
            return (fallbackCode != null)
                    ? messageSource.getMessage(fallbackCode, new String[]{code}, request.getLocale())
                    : code;
        }
    }

    /* ServiceApp RELATED Exception ========================= */
    @ExceptionHandler(value = {ServiceAppException.class})
    protected ResponseEntity<Object> handleServiceAppException(ServiceAppException ex, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.valueOf(ex.getServiceAppStatusCode());

        if (HttpStatus.INTERNAL_SERVER_ERROR == httpStatus) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }

        if (ex.getUseErrorMessage()) {
            final String i18nMessage = getI18nMessage(ex.getMessage(), "atfApp.handleServiceAppException.fallback",
                    request, ex.getMessageArgs());
            return ResponseEntity.status(httpStatus).body(new ResponseEntity(new ResponseDto(i18nMessage, ex.getMessage(), httpStatus), httpStatus));
        } else {
            if (ex.getMessageArgs().length > 1) {
                return ResponseEntity.status(httpStatus).body(new ResponseEntity(new ResponseDto(ex.getMessageArgs(), ex.getMessage(), httpStatus), httpStatus));
            } else {
                return ResponseEntity.status(httpStatus).body(new ResponseEntity(new ResponseDto(ex.getMessageArgs()[0], ex.getMessage(), httpStatus), httpStatus));
            }
        }
    }
}
