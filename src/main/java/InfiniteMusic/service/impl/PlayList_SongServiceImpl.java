package InfiniteMusic.service.impl;

import InfiniteMusic.dao.PlayList_SongDao;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.service.PlayList_SongService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayList_SongServiceImpl extends ServiceImpl<PlayList_SongDao, PlayList_Song> implements PlayList_SongService {

    @Autowired
    PlayList_SongDao playListSongDao;

    //对于歌单内歌曲的操作
    //包括对歌单内增加歌曲，减少歌曲和查询，传输类型为单个或列表
    //增加单个歌曲
    @Transactional
    public void addOneSong(int playListId,int songId) {
        PlayList_Song playList_song = new PlayList_Song(playListId,songId);
        playListSongDao.insert(playList_song);
    }

    //添加一列表的歌曲
    @Transactional
    public void addAllSong(int playListId, List<Integer> songIdList){
        for(int songId:songIdList){
            addOneSong(playListId,songId);
        }
    }

    //删除单个歌曲
    @Transactional
    public void deleteOneSong(int playListId,int songId) {
        LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
        lqw.eq(PlayList_Song::getPlaylistid,playListId).eq(PlayList_Song::getSongid,songId);
        playListSongDao.delete(lqw);
    }

    //查询歌单内的所有歌曲Id
    public List<Integer> findSongsinList(int playListId){
        LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
        lqw.eq(PlayList_Song::getPlaylistid,playListId);
        List<PlayList_Song> result = playListSongDao.selectList(lqw);
//        if(result==null){
//
//        }
        List<Integer> songs = new ArrayList<>();
        for (PlayList_Song playList_song : result) {
             songs.add(playList_song.getSongid());
        }
        return songs;
    }

    //返回歌单的个数
    public int finsSongsNumber(int playListId){
        return findSongsinList(playListId).size();
    }

    //歌单整体操作后对关联表的影响
    //删除歌单时删去关联表
    @Transactional
    public void deleteByListId(int playListId) {
        LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
        lqw.eq(PlayList_Song::getPlaylistid,playListId);
        playListSongDao.delete(lqw);
    }

}
