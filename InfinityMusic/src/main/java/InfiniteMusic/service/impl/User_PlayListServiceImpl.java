package InfiniteMusic.service.impl;

import InfiniteMusic.dao.PlayListDao;
import InfiniteMusic.dao.PlayList_SongDao;
import InfiniteMusic.dao.User_PlayListDao;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.entity.User;
import InfiniteMusic.entity.User_PlayList;
import InfiniteMusic.service.PlayListService;
import InfiniteMusic.service.PlayList_SongService;
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
    public void addPlayListCreate(int userId,Long playListId) {
        User_PlayList user_PlayList = new User_PlayList(userId,playListId,true);
        userPlayListDao.insert(user_PlayList);//添加到用户的创建歌单列表
    }

    //添加用户歌单（收藏）
    @Transactional
    public void addPlayListLike(int userId,Long playListId) {
        User_PlayList user_PlayList = new User_PlayList(userId,playListId,false);
        userPlayListDao.insert(user_PlayList);//添加到用户的喜欢歌单列表
    }


    //删除创建用户的歌单
    @Transactional
    public void deleteCreatePlayList(Long playListId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getPlaylist_id,playListId).eq(User_PlayList::isCreate_like,true);
        userPlayListDao.delete(lqw);
    }

    //删除用户喜欢的歌单
    public void deleteLikePlayList(Long playListId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getPlaylist_id,playListId).eq(User_PlayList::isCreate_like,false);
        userPlayListDao.delete(lqw);
    }

   //查询用户创建的歌单的id
    public List<Long> getCreateListId(int userId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getId,userId).eq(User_PlayList::isCreate_like,true);
        List<User_PlayList> user_PlayList=userPlayListDao.selectList(lqw);
        List<Long> lists = new ArrayList<>();
        for(User_PlayList userPlayList:user_PlayList){
            lists.add(userPlayList.getPlaylist_id());
        }
        return lists;
    }

    //查询用户喜欢的歌单的id
    public List<Long> getLikeListId(int userId) {
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getId,userId).eq(User_PlayList::isCreate_like,false);
        List<User_PlayList> user_PlayList=userPlayListDao.selectList(lqw);
        List<Long> lists = new ArrayList<>();
        for(User_PlayList userPlayList:user_PlayList){
            lists.add(userPlayList.getPlaylist_id());
        }
        return lists;
    }

    //查询一个歌单的创建者
    public int getListCreator(Long playListId){
        LambdaQueryWrapper<User_PlayList> lqw = new LambdaQueryWrapper<User_PlayList>();
        lqw.eq(User_PlayList::getPlaylist_id,playListId).eq(User_PlayList::isCreate_like,true);
        User_PlayList userPlay=userPlayListDao.selectOne(lqw);
        return userPlay.getId();
    }

}
