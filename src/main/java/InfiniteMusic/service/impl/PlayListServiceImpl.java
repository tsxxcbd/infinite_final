package InfiniteMusic.service.impl;

import InfiniteMusic.dao.*;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.service.PlayListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayListServiceImpl extends ServiceImpl<PlayListDao, PlayList> implements PlayListService {

    @Autowired
    PlayListDao playListdao;

    //对于歌单的整体操作-歌单的创建，删除，获取
    //创建歌单
    @Transactional
    public PlayList createPlayList(String name,String profile) {
        PlayList playList = new PlayList();
        playList.setName(name);
        playList.setProfile(profile);
        playListdao.insert(playList);
        return playList;
    }

    @Transactional
    public int createLikeSongList(){
        PlayList playList = new PlayList();
        playListdao.insert(playList);
        return playList.getPlaylistid();
    }

    //获取歌单
    public PlayList getPlayList(int playlistId){
        LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
        lqw.eq(PlayList::getPlaylistid,playlistId);
        PlayList playList = playListdao.selectOne(lqw);
        //如果找不到对象，在controller层包装
        return playList;
    }

    public List<PlayList> getListPlayList(List<Integer> Lists){
        List<PlayList> playLists = new ArrayList<PlayList>();
        for(int id : Lists){
            playLists.add(getPlayList(id));
        }
        return playLists;
    }

    //删除歌单
    @Transactional
    public void deletePlayList(int playlistId){
        LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
        lqw.eq(PlayList::getPlaylistid,playlistId);
        playListdao.delete(lqw);
    }


}
