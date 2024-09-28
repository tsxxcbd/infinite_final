package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="song")
public class Song {

    @TableId
    private int songid;
    private String songname;
    private int albumid;
    private int musicianid;
    private String emotion;
    private String lyrics;
    private String artist;
    private String album;

}
