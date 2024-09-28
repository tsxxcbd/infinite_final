package InfiniteMusic.result;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.json.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


@Data
public class JsonResponse <T>{


    public static<T> String OK(T data) {

        return JSON.toJSONString(Output.SucceedPara(data),
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat,
                SerializerFeature.DisableCircularReferenceDetect);

    }

    public static<T> String OK() {

        return JSON.toJSONString(Output.SucceedVoid(),
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat,
                SerializerFeature.DisableCircularReferenceDetect);

    }

    public static<T> String Fail(String msg){

        return JSON.toJSONString(Output.FailPara(msg),
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat,
                SerializerFeature.DisableCircularReferenceDetect);


    }

}
