package InfiniteMusic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import InfiniteMusic.entity.Song;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongDao extends BaseMapper<Song> {
}
