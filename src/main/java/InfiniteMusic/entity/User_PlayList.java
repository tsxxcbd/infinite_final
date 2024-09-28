package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_playlist")
public class User_PlayList {
    private int userid;//用户id
    private int playlistid;
    private boolean createorlike;

    public User_PlayList(int userId,int playListId,boolean createOrLike) {
        this.userid =userId;
        this.playlistid =playListId;
        this.createorlike = createOrLike;
        //true为创建，false为喜欢
    }
}
