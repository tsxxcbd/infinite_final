package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="playList_song")
public class PlayList_Song {
    //PLayList和Song的对应关系
    private int playlistid;
    private int songid;

    public PlayList_Song(int playlistId,int SongId){
        this.playlistid =playlistId;
        this.songid =SongId;
    }

}
