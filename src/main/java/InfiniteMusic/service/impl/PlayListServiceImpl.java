package InfiniteMusic.service.impl;

import InfiniteMusic.dao.*;
import InfiniteMusic.entity.PlayList;
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
    public PlayList createPlayList(String name,String profile) throws Exception{

        try{
            PlayList playList = new PlayList();
            playList.setName(name);
            playList.setProfile(profile);
            playListdao.insert(playList);
            return playList;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public int createLikeSongList()throws Exception{

        try{
            PlayList playList = new PlayList();
            playListdao.insert(playList);
            return playList.getPlaylistid();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //获取歌单
    public PlayList getPlayList(int playlistId)throws Exception{

        try{
            LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
            lqw.eq(PlayList::getPlaylistid,playlistId);
            PlayList playList = playListdao.selectOne(lqw);
            if(playList==null){
                throw new Exception("歌单未能获取成功");
            }
            return playList;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<PlayList> getListPlayList(List<Integer> Lists)throws Exception{

        try{
            List<PlayList> playLists = new ArrayList<PlayList>();
            for(int id : Lists){
                playLists.add(getPlayList(id));
            }
            return playLists;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //删除歌单
    @Transactional
    public void deletePlayList(int playlistId)throws Exception{

        try{
            LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
            lqw.eq(PlayList::getPlaylistid,playlistId);
            int rowsAffected = playListdao.delete(lqw);
            if(rowsAffected==0){
                throw new Exception("没有找到对应的播放列表，删除操作未成功");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
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
