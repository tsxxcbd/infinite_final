package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private int commentid;
    private String content;
    private  int userid;
    private int songid;
    private  Data time;

    public Comment (int song_id, String content){
        this.songid =song_id;
        this.content=content;
    }
}
