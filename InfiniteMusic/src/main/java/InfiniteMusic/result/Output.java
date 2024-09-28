package InfiniteMusic.result;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.dynalink.beans.StaticClass;
import lombok.Data;
import org.bson.json.JsonObject;

@Data
@JSONType(orders = {"status","msg","data"})
public class Output<T> {

    //状态码
    private int status;
    //信息
    private String msg;
    //数据
    private T data;

    //构造私有化
    private Output() { }
    private Output(int status,String msg,T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    //设置数据,返回对象的方法
    public static Output SucceedVoid(){
        Output output = new Output(0,"Ok",null);
        return output;
    }

    public static<T> Output SucceedPara(T data){
        Output output = new Output(0,"Ok",data);
        return output;
    }

    public static<T> Output FailPara(String msg){
        Output output = new Output(1,msg,null);
        return output;
    }



}

