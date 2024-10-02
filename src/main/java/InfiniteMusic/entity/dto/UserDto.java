package InfiniteMusic.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String password;

//    private String sex;
//
//    private Integer age;
//
//    private String nickname;
//
//    //private Date createTime;
//
//    @TableField("likelist_id")
//    private Long likelistId;
}
