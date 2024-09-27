package InfiniteMusic.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String password;

    private String sex;

    private int age;

    private String nickname;

    private Date createTime;

    private Long likelistId;
}
