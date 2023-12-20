package InfiniteMusic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayListException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(PlayListException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(exception.getCode());
        response.setMessage(exception.getMessage());
        ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionResponse> handleUserExceptions(UserException exception,WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(exception.getCode());
        response.setMessage(exception.getMessage());
        ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }
    //自己定义的新的异常类往下面写
}

