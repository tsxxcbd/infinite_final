package InfiniteMusic.service.impl;

import InfiniteMusic.dao.PlayList_SongDao;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.service.PlayList_SongService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayList_SongServiceImpl extends ServiceImpl<PlayList_SongDao, PlayList_Song> implements PlayList_SongService {

    @Autowired
    PlayList_SongDao playListSongDao;
    @Autowired
    private MongoClient mongoClient;

    //对于歌单内歌曲的操作
    //包括对歌单内增加歌曲，减少歌曲和查询，传输类型为单个或列表
    //增加单个歌曲
    @Transactional
    public void addOneSong(Long playListId,Long songId) {
        PlayList_Song playList_song = new PlayList_Song(playListId,songId);
        playListSongDao.insert(playList_song);
    }

    //添加一列表的歌曲
    @Transactional
    public void addAllSong(Long playListId, List<Long> songIdList){
        for(Long songId:songIdList){
            addOneSong(playListId,songId);
        }
    }

    //删除单个歌曲
    @Transactional
    public void deleteOneSong(Long playListId,Long songId) {
        LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
        lqw.eq(PlayList_Song::getId,playListId).eq(PlayList_Song::getSong_id,songId);
        playListSongDao.delete(lqw);
    }

    //查询歌单内的所有歌曲Id
    public List<Long> findSongsinList(Long playListId){
        LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
        lqw.eq(PlayList_Song::getId,playListId);
        List<PlayList_Song> result = playListSongDao.selectList(lqw);
        List<Long> songs = new ArrayList<>();
        for (PlayList_Song playList_song : result) {
             songs.add(playList_song.getSong_id());
        }
        return songs;
    }

    //返回歌单的个数
    public int finsSongsNumber(long playListId){
        return findSongsinList(playListId).size();
    }

    //歌单整体操作后对关联表的影响
    //删除歌单时删去关联表
    @Transactional
    public void deleteByListId(Long playListId) {
        LambdaQueryWrapper<PlayList_Song> lqw = new LambdaQueryWrapper<PlayList_Song>();
        lqw.eq(PlayList_Song::getId,playListId);
        playListSongDao.delete(lqw);
    }


    @Override
    public List<listSongSearchResult> searchSong(List<Long> songIDs) {
        MongoDatabase mDatabase = mongoClient.getDatabase("SONGLIST");
        MongoCollection<Document> collection = mDatabase.getCollection("songs");
        List<listSongSearchResult> searchResults = new ArrayList<>();


        for (Long id : songIDs) {
            Document query = new Document("song_id", id);
            Document result = collection.find(query).first();

            if (result != null) {
                String songName = result.getString("song_name");
                String artist = result.getString("artist");
                String album = result.getString("album");

                listSongSearchResult searchResult = new listSongSearchResult(songName, artist, album,null);
                searchResult.setSongid(id);
                searchResults.add(searchResult);
            }
        }

        return searchResults;
    }
}
