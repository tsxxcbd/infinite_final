package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "record")
public class Record {

    @TableId(type= IdType.AUTO)
    private int recordid;
    private int songid;
    private Data time;

}
