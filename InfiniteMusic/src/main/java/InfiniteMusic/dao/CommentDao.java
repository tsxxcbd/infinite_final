package InfiniteMusic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import InfiniteMusic.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao extends BaseMapper<Comment> {
}
