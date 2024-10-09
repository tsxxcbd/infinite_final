package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "musician")
public class Musician {

    @TableId(type = IdType.AUTO)
    private int musicianid;
    private String name;
    private String profile;
}