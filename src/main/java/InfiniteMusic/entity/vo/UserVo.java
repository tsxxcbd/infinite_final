package InfiniteMusic.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;

    private String password;

    private String sex;

    private int age;

    private String nickname;

    private Date createTime;

    private int likelistId;
}
