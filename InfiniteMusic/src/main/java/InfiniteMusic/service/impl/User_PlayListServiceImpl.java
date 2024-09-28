package InfiniteMusic.service.impl;

import InfiniteMusic.dao.User_PlayListDao;
import InfiniteMusic.entity.User_PlayList;
import InfiniteMusic.service.User_PlayListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class User_PlayListServiceImpl extends ServiceImpl<User_PlayListDao, User_PlayList> implements User_PlayListService {
    @Autowired
    User_PlayListDao userPlayListDao;

    //添加用户歌单（创建）
    @Transactional
    public void addPlayListCreate(int userId,int playListId) {
        User_PlayList user_PlayList = new User_PlayList(userId,playListId,true);
        userPlayListDao.insert(user_PlayList);//添加到用户的创建歌单列表
    }

    //添加用户歌单（收藏）
    @Transactional
    public void addPlayListLike(int userId,int playListId) {
        User_PlayList user_PlayList = new User_PlayList(userId,playListId,false);
        userPlayListDao.insert(user_PlayList);//添加到用户的喜欢歌单列表
    }


    //删除创建用户的歌单
    @Transactional
    public void deleteCreatePlayList(int playListId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getPlaylistid,playListId).
                eq(User_PlayList::isCreateorlike,true);
        userPlayListDao.delete(lqw);
    }

    //删除用户喜欢的歌单
    public void deleteLikePlayList(int userid ,int playListId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getPlaylistid,playListId).
                eq(User_PlayList::isCreateorlike,false).
                eq(User_PlayList::getUserid,userid);
        userPlayListDao.delete(lqw);
    }

   //查询用户创建的歌单的id
    public List<Integer> getCreateListId(int userId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getUserid,userId).
                eq(User_PlayList::isCreateorlike,true);
        List<User_PlayList> user_PlayList=userPlayListDao.selectList(lqw);
        List<Integer> lists = new ArrayList<>();
        for(User_PlayList userPlayList:user_PlayList){
            lists.add(userPlayList.getPlaylistid());
        }
        return lists;
    }

    //查询用户喜欢的歌单的id
    public List<Integer> getLikeListId(int userId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getUserid,userId).eq(User_PlayList::isCreateorlike,false);
        List<User_PlayList> user_PlayList=userPlayListDao.selectList(lqw);
        List<Integer> lists = new ArrayList<>();
        for(User_PlayList userPlayList:user_PlayList){
            lists.add(userPlayList.getPlaylistid());
        }
        return lists;
    }

    //查询一个歌单的创建者
    public int getListCreator(int playListId){
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getPlaylistid,playListId).eq(User_PlayList::isCreateorlike,true);
        User_PlayList userPlay=userPlayListDao.selectOne(lqw);
        return userPlay.getUserid();
    }

}
