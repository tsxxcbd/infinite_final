package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@TableName(value="playList_song")
public class PlayList_Song {
    //PLayList和Song的对应关系
    private int id;
    private int song_id;

    public PlayList_Song(int playlistId,int SongId){
        this.id=playlistId;
        this.song_id=SongId;
    }

}
