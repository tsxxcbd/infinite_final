package InfiniteMusic.dao;

import InfiniteMusic.entity.Record;
import InfiniteMusic.entity.Song;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RecordDao extends BaseMapper<Record> {
}
