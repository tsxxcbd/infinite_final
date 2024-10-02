//package InfiniteMusic.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(PlayListException.class)
//    public ResponseEntity<ExceptionResponse> handleExceptions(PlayListException exception, WebRequest webRequest) {
//        ExceptionResponse response = new ExceptionResponse();
//        response.setCode(exception.getCode());
//        response.setMessage(exception.getMessage());
//        ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        return entity;
//    }
//    @ExceptionHandler(UserException.class)
//    public ResponseEntity<ExceptionResponse> handleUserExceptions(UserException exception,WebRequest webRequest){
//        ExceptionResponse response = new ExceptionResponse();
//        response.setCode(exception.getCode());
//        response.setMessage(exception.getMessage());
//        ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        return entity;
//    }
//    //自己定义的新的异常类往下面写
//}
//
package InfiniteMusic.exception;

import InfiniteMusic.auth.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail(null);
    }

    /**
     * 自定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(InfiniteException.class)
    @ResponseBody
    public Result error(InfiniteException e){
        return Result.build(null,e.getCode(), e.getMessage());
    }
}
