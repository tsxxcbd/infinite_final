package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_playlist")
public class User_PlayList {
    private int id;//用户id
    private Long playlist_id;
    private boolean create_like;

    public User_PlayList(int userId,long playListId,boolean createOrLike) {
        this.id=userId;
        this.playlist_id=playListId;
        this.create_like = createOrLike;
        //true为创建，false为喜欢
    }
}
