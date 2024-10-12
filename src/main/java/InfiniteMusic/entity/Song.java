package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="song")
public class Song {

    @TableId
    @TableField("id")
    private int id;
    private String songName;
    private int albumId;
    private int musicianId;
    private String emotion;
    private String lyrics;
    private String artist;
    private String album;

}