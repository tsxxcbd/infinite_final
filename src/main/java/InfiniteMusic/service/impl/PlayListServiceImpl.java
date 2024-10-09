package InfiniteMusic.service.impl;

import InfiniteMusic.auth.ResultCodeEnum;
import InfiniteMusic.dao.*;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.exception.InfiniteException;
import InfiniteMusic.service.PlayListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.element.Name;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayListServiceImpl extends ServiceImpl<PlayListDao, PlayList> implements PlayListService {

    @Autowired
    PlayListDao playListdao;

    //对于歌单的整体操作-歌单的创建，删除，获取
    //创建歌单
    @Transactional
    public PlayList createPlayList(String name,String profile){
        PlayList playList = new PlayList();
        playList.setName(name);
        playList.setProfile(profile);
        playListdao.insert(playList);
        return playList;
    }

    @Transactional
    public Long createLikeSongList()throws Exception{
        PlayList playList = new PlayList();
        playListdao.insert(playList);
        return playList.getId();
    }

    //获取歌单
    public PlayList getPlayList(Long playlistId){
        LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
        lqw.eq(PlayList::getId,playlistId);
        PlayList playList = playListdao.selectOne(lqw);
        return playList;
    }

    public List<PlayList> getListPlayList(List<Integer> Lists){
        List<PlayList> playLists = new ArrayList<PlayList>();
        for(int id : Lists){
            playLists.add(getPlayList((long) id));
        }
        return playLists;
    }

    //删除歌单
    @Transactional
    public void deletePlayList(int playlistId)throws Exception{
        LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
        lqw.eq(PlayList::getId,playlistId);
        int rowsAffected = playListdao.delete(lqw);
        if(rowsAffected==0){
            throw new InfiniteException(ResultCodeEnum.NO_PLAYLIST_FOUND);
        }
    }

    //根据歌单简介查找歌单信息
    public List<PlayList> searchListByProfile(String profile)throws Exception{

        try{
            LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
            lqw.eq(PlayList::getProfile,profile);
            return playListdao.selectList(lqw);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //根据歌单名字查找歌单信息
    public List<PlayList> searchListByName(String name)throws Exception{

        try{
            LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
            lqw.eq(PlayList::getName, name);
            return playListdao.selectList(lqw);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }




}