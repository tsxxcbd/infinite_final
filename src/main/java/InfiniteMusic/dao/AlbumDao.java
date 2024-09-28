package InfiniteMusic.dao;

import InfiniteMusic.entity.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlbumDao extends BaseMapper<Album> {
}
