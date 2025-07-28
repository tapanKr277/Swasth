package com.org.swasth_id_backend.exception;

import com.org.swasth_id_backend.dto.CustomErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorMessageDto> globalException(Exception exp, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(exp.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.BAD_REQUEST.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<CustomErrorMessageDto> InvalidOtpException(InvalidOtpException invalidOtpException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(invalidOtpException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.BAD_REQUEST.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAbleToSendEmail.class)
    public ResponseEntity<CustomErrorMessageDto> notAbleToSendEmailException(NotAbleToSendEmail notAbleToSendEmail, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(notAbleToSendEmail.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OtpNotFound.class)
    public ResponseEntity<CustomErrorMessageDto> otpNotFoundException(OtpNotFound otpNotFound, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(otpNotFound.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.NOT_FOUND.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordMisMatchException.class)
    public ResponseEntity<CustomErrorMessageDto> passwordMisMatchException(PasswordMisMatchException passwordMisMatchException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(passwordMisMatchException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.BAD_REQUEST.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorMessageDto> resourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(resourceNotFoundException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.NOT_FOUND.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<CustomErrorMessageDto> samePasswordException(SamePasswordException samePasswordException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(samePasswordException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.NOT_FOUND.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomErrorMessageDto> userNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(userNotFoundException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.NOT_FOUND.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<CustomErrorMessageDto> userNotVerifiedException(UserNotVerifiedException userNotVerifiedException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(userNotVerifiedException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.BAD_REQUEST.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<CustomErrorMessageDto> wrongPasswordException(WrongPasswordException wrongPasswordException, WebRequest webRequest){
        CustomErrorMessageDto customErrorMessageDto = new CustomErrorMessageDto();
        customErrorMessageDto.setError(wrongPasswordException.getMessage());
        customErrorMessageDto.setPath(webRequest.getDescription(false));
        customErrorMessageDto.setStatus(HttpStatus.BAD_REQUEST.value());
        customErrorMessageDto.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(customErrorMessageDto, HttpStatus.BAD_REQUEST);
    }

}
