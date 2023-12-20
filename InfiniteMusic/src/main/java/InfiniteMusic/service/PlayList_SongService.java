package InfiniteMusic.service;

import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.PlayList_Song;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PlayList_SongService extends IService<PlayList_Song> {
    List<listSongSearchResult>searchSong(List<Long> songIDs);
    class listSongSearchResult{
        private String songName;
        private String artist;
        private String album;
        private  Long  songid;

        public listSongSearchResult(String songName, String artist,String album,Long songid) {
            this.songName = songName;
            this.artist = artist;
            this.album= album;
            this.songid=songid;
        }

        public String getSongName() {
            return songName;
        }


        public String getArtist() {
            return artist;
        }
        public String getAlbum(){return  album;}
        public Long getSongid(){return songid;}

        public void setSongid(Long songid) {
            this.songid = songid;
        }
    }
}
