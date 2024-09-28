package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private int id;
    private String content;
    private  int user_id;
    private int song_id;
    private  Data time;

    public Comment (int song_id, String content){
        this.song_id=song_id;
        this.content=content;
    }
}
