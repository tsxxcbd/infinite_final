package InfiniteMusic.auth;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
    FAIL(201, "fail"),

    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    DUPLICATE_USERNAME(207, "用户名重复"),
    ILLEGALPARAM(208, "非法参数"),
    WRONG_USERNAME(209, "用户名错误"),
    WRONG_PASSWORD(210, "密码错误"),;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

