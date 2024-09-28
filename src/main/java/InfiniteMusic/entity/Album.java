package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "album")
public class Album {

    @TableId(type = IdType.AUTO)
    private int albumid;
    private String name;
    private int musicianid;

}
