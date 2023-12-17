package InfiniteMusic.service;

import lombok.Data;
import lombok.Getter;

import java.util.List;

public interface SearchService {
    List<SearchResult> search(String keyword);

    // 自定义实体类，表示搜索结果
    @Getter
    class SearchResult {
        // 确保存在正确的 getter 方法
        private Integer  song_id;
        private String songName;
        private String artist;
        private String album;

        public SearchResult(Integer song_id,String songName, String artist, String album) {
            this.song_id=song_id;
            this.songName = songName;
            this.artist = artist;
            this.album = album;
        }

    }
}
