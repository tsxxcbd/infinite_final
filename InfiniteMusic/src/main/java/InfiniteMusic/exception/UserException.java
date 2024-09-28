package InfiniteMusic.exception;

import lombok.Data;

@Data
public class UserException extends Exception {

    //定义各种错误代码常量
    public static final int WRONG_PASSWORD = 600;
    public static final int DUPLICATE_USERNAME =601;
    public static final int ILLEGALPARAM=602;
    public static final int WRONG_USERNAME=603;
    public static final int NO_SEARCH_RESULT=604;
   private int code; //自定义的错误代码
    private String messege;//
    public UserException(int code,String message){
        super(message);
        this.code= code;
    }
}
