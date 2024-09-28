package InfiniteMusic.dao;

import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.PlayList_Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlayList_SongDao extends BaseMapper<PlayList_Song> {
}
