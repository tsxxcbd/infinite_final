package InfiniteMusic.service.impl;

import InfiniteMusic.dao.UserDao;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.entity.User;
import InfiniteMusic.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

    @Service
    public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

        @Autowired
        UserDao userDao;

        @Transactional
        public User findByUsername(String username) {
            User user=userDao.findByUsername(username);
            return user;
        }

        @Transactional
        public void register(String username, String password,Long likelistId) {
//            User user = new User();
//            user.setName(username);
//            user.setPassword(password);
//            user.setLikelistId(likelistId);
            userDao.add(username,password,likelistId);
        }

        /*@Transactional
        public void setlikeSong(String username,Long likeSongid){
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
            lqw.eq(User::getName,username);
            User user = userDao.selectOne(lqw);
            userDao.delete(lqw);
            user.setLikelistId(likeSongid);
            userDao.insert(user);
        }*/




}
