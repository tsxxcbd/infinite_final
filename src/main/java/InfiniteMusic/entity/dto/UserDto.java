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

//
//
//    @TableField("likelist_id")
//    private Long likelistId;
}
