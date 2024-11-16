package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "comment_song_user")
public class Comment_Song_User implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long song_id;//外键关联
    private int user_id;
    private Long id; //主键为commentid ，上面两个为外键
}
