package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@TableName(value="playList_song")
public class PlayList_Song {
    //PLayList和Song的对应关系
    private Long id;
    private Long songId;

    public PlayList_Song(Long playlistId,Long SongId){
        this.id=playlistId;
        this.songId=SongId;
    }

}
