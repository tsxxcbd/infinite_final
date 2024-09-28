package InfiniteMusic.exception;

import lombok.Data;

@Data
public class PlayListException extends Exception{

    //定义各种错误代码常量
    public final static int NOT_FOUND_BY_ID = 100;

    int code; //自定义的错误代码
    public PlayListException(int code,String message){
        super(message);
        this.code=code;
    }

}
