package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@TableName(value = "comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;
    private  int user_id;
    private Long song_id;
    private  String time;
}
