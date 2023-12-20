package InfiniteMusic.dao;

import InfiniteMusic.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface UserDao extends BaseMapper<User> {
  //根据用户名查询用户
    @Select("select * from user where name=#{username}")
    User findByUsername(String username);
//添加用户
    @Insert("insert into user (name,password,createtime,likelistId)"+
            " values(#{username},#{password},now(),#{likelistId})")
    void add(String username, String password,Long likelistId);

    @Select("select * from user where id=#{id}")
    User findById(int id);



}

