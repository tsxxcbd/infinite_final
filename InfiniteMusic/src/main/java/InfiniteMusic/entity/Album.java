package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "album")
public class Album {

    @TableId
    private Long id;
    private  String name;
}
