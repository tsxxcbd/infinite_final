package InfiniteMusic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import InfiniteMusic.entity.Musician;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicianDao extends BaseMapper<Musician> {
}
