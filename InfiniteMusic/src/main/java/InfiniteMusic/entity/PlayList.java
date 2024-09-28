package InfiniteMusic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
@TableName(value="playlist")
public class PlayList {

    //存放歌单的细节
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private int playlistid;
    private String name;
    private String profile;
    private int number;
    private String creatorname;

    public PlayList(){}

    //如果是用户自己创建的歌单需要为整个赋一个新的id

}
