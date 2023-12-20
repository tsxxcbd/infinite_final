package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "musician")
public class Musician {

    @TableId
    private Long id;
    private String name;
    private Long album_id;
    private String profile;
}
