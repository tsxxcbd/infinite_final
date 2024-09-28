package InfiniteMusic.service.impl;

import InfiniteMusic.dao.SongDao;
import InfiniteMusic.entity.Song;
import InfiniteMusic.service.SongService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl extends ServiceImpl<SongDao, Song> implements SongService {

    @Autowired
    SongDao songDao ;

    @Transactional
    public Song CreatSong(int song_id, String song_name,String emotion
            ,int album_id,int musician_id,String album,String artist)throws Exception{

        try {
            Song song = new Song();
            song.setSongid(song_id);
            song.setSongname(song_name);
            song.setEmotion(emotion);
            song.setAlbumid(album_id);
            song.setMusicianid(musician_id);
            song.setAlbum(album);
            song.setArtist(artist);
            songDao.insert(song);

            return song;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public List<Song> searchSong(List<Integer> songIDs) throws Exception{

        List<Song> searchResults = new ArrayList<>();

        for (int id : songIDs) {
            LambdaQueryWrapper<Song> lqw = new LambdaQueryWrapper<Song>();
            lqw.eq(Song::getSongid,id);
            Song song = songDao.selectById(id);
            searchResults.add(song);
        }

        return searchResults;

    }

    public List<Song> searchAll(String keyword) throws Exception{

        List<Song> songs = new ArrayList<Song>();
        songs.addAll(searchBySongName(keyword));
        songs.addAll(searchByArtist(keyword));
        songs.addAll(searchByAlbum(keyword));
        songs.addAll(searchByEmotion(keyword));
        return songs;
    }

    public List<Song> searchBasicInfo(String keyword) throws Exception{

        List<Song> songs = new ArrayList<Song>();
        songs.addAll(searchBySongName(keyword));
        songs.addAll(searchByArtist(keyword));
        songs.addAll(searchByAlbum(keyword));
        return songs;
    }



    public List<Song> searchBySongName(String song_name) throws Exception{

        LambdaQueryWrapper<Song> lqw = new LambdaQueryWrapper<Song>();
        lqw.like(Song::getSongname, "%" + song_name + "%"); // 使用 like 方法进行模糊搜索
        return songDao.selectList(lqw);

    }

    public List<Song> searchByArtist(String artist) throws Exception{

        LambdaQueryWrapper<Song> lqw = new LambdaQueryWrapper<Song>();
        lqw.like(Song::getArtist, "%" + artist + "%"); // 使用 like 方法进行模糊搜索
        return songDao.selectList(lqw);

    }

    public List<Song> searchByAlbum(String album) throws Exception{

        LambdaQueryWrapper<Song> lqw = new LambdaQueryWrapper<Song>();
        lqw.like(Song::getAlbum, "%" + album + "%"); // 使用 like 方法进行模糊搜索
        return songDao.selectList(lqw);

    }

    public List<Song> searchByEmotion(String emotion) throws Exception{

        LambdaQueryWrapper<Song> lqw = new LambdaQueryWrapper<Song>();
        lqw.like(Song::getEmotion, "%" + emotion + "%"); // 使用 like 方法进行模糊搜索
        return songDao.selectList(lqw);

    }



}
