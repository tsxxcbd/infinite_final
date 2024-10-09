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
    public void addOneSong(Long playListId,Long songId)throws Exception {

        try{
            PlayList_Song playList_song = new PlayList_Song(playListId,songId);
            playListSongDao.insert(playList_song);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    //添加一列表的歌曲
    @Transactional
    public void addAllSong(Long playListId, List<Integer> songIdList)throws Exception{

        try{
            for(int songId:songIdList){
                addOneSong(playListId, (long) songId);
            }
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    //删除单个歌曲
    @Transactional
    public void deleteOneSong(int playListId,int songId) throws Exception{

        try{
            LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
            lqw.eq(PlayList_Song::getId,playListId).eq(PlayList_Song::getSong_id,songId);
            playListSongDao.delete(lqw);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    //查询歌单内的所有歌曲Id
    public List<Integer> findSongsinList(Long playListId)throws Exception{

        try{
            LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
            lqw.eq(PlayList_Song::getId,playListId);
            List<PlayList_Song> result = playListSongDao.selectList(lqw);
            if(result==null){
                throw new Exception("该歌单不存在");
            }
            List<Integer> songs = new ArrayList<>();
            for (PlayList_Song playList_song : result) {
                songs.add(Math.toIntExact(playList_song.getSong_id()));
            }
            return songs;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    //返回歌单的个数
    public int finsSongsNumber(Long playListId)throws Exception{
        return findSongsinList(playListId).size();
    }

    //歌单整体操作后对关联表的影响
    //删除歌单时删去关联表
    @Transactional
    public void deleteByListId(int playListId) throws Exception{
        try {
            LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
            lqw.eq(PlayList_Song::getId,playListId);
            playListSongDao.delete(lqw);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}