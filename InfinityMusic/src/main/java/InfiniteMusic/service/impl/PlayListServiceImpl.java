package InfiniteMusic.service.impl;

import InfiniteMusic.dao.*;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.Song;
import InfiniteMusic.exception.PlayListException;
import InfiniteMusic.service.PlayListService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    public Long createLikeSongList(){
        PlayList playList = new PlayList();
        playListdao.insert(playList);
        return playList.getId();
    }

    //获取歌单
    public PlayList getPlayList(Long playlistId){
        LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
        lqw.eq(PlayList::getId,playlistId);
        PlayList playList = playListdao.selectOne(lqw);
        //如果找不到对象，在controller层包装
        return playList;
    }

    public List<PlayList> getListPlayList(List<Long> Lists){
        List<PlayList> playLists = new ArrayList<PlayList>();
        for(Long id : Lists){
            playLists.add(getPlayList(id));
        }
        return playLists;
    }

    //删除歌单
    @Transactional
    public void deletePlayList(Long playlistId){
        LambdaQueryWrapper<PlayList> lqw = new LambdaQueryWrapper<PlayList>();
        lqw.eq(PlayList::getId,playlistId);
        playListdao.delete(lqw);
    }

}
