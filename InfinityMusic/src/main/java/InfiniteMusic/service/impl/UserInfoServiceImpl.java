package InfiniteMusic.service.impl;

import InfiniteMusic.dao.UserDao;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.User;
import InfiniteMusic.exception.UserInfoException;
import InfiniteMusic.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserDao, User> implements UserInfoService {
    @Autowired
    UserDao userDao;

    //获取用户
    @Transactional
    public User getUser(int userId) {
        User user = userDao.findById(userId);
        //如果找不到对象，在controller层包装
        return user;
    }

    //获取用户的likelistid
    public Long getlikelistid(int userId) {
        User user = userDao.findById(userId);
        //如果找不到对象，在controller层包装
        return user.getLikelistId();
    }

    //获取用户的用户名
    public String getusername(int userId) {
        User user = userDao.findById(userId);
        //如果找不到对象，在controller层包装
        return user.getName();
    }

    /*@Transactional
    public int updateUser(int userId,User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getId,userId);
        int result = userDao.updateById(user);
        //如果找不到对象，在controller层包装
        return result;
    }*/

}
