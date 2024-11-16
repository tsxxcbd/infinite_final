package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "user_playlist")
public class User_PlayList implements   java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;//用户id
    @TableField("playlist_id")
    private Long playlistId;

    @TableField("create_like")
    private boolean createLike;

    public User_PlayList(Long userId,Long playListId,boolean createOrLike) {
        this.id=userId;
        this.playlistId=playListId;
        this.createLike = createOrLike;
        //true为创建，false为喜欢
    }
}
