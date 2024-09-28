package InfiniteMusic.controller;

import InfiniteMusic.entity.Song;
import InfiniteMusic.exception.UserException;
import InfiniteMusic.service.SongService;
import InfiniteMusic.service.impl.AlbumServiceImpl;
import InfiniteMusic.service.impl.MusicianServiceImpl;
import InfiniteMusic.service.impl.SongServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "歌曲管理")
@RestController
@RequestMapping("/search")
public class SearchController {


    @Autowired
    SongServiceImpl songService;
    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    MusicianServiceImpl musicianService;


    @ApiOperation("总体查询")
    @GetMapping("")
    public ResponseEntity<List<Song>> searchResults(@RequestParam(name = "keyword", required = false) String keyword) {

        List<Song> results = songService.searchAll(keyword);
        // 如果没有搜索到结果，返回404的状态码和消息
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);

    }

    @ApiOperation("根据歌名查询")
    @GetMapping("/songName")
    public ResponseEntity<List<Song>> searchBySongNme(@RequestParam(name = "keyword", required = false) String keyword) {

        List<Song> results = songService.searchBySongName(keyword);
        // 如果没有搜索到结果，返回404的状态码和消息
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);

    }

    @ApiOperation("根据歌手查询")
    @GetMapping("/artist")
    public ResponseEntity<List<Song>> searchByArtist(@RequestParam(name = "keyword", required = false) String keyword) {

        List<Song> results = songService.searchByArtist(keyword);
        // 如果没有搜索到结果，返回404的状态码和消息
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);

    }

    @ApiOperation("根据专辑查询")
    @GetMapping("/album")
    public ResponseEntity<List<Song>> searchByAlbum(@RequestParam(name = "keyword", required = false) String keyword) {

        List<Song> results = songService.searchByAlbum(keyword);
        // 如果没有搜索到结果，返回404的状态码和消息
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);

    }

    @ApiOperation("根据心情查询")
    @GetMapping("/emotion")
    public ResponseEntity<List<Song>> searchByEmotion(@RequestParam(name = "keyword", required = false) String keyword) {

        List<Song> results = songService.searchByEmotion(keyword);
        // 如果没有搜索到结果，返回404的状态码和消息
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);

    }

    @ApiOperation("根据歌曲基本信息查询，即歌名，歌手，专辑")
    @GetMapping("/basic")
    public ResponseEntity<List<Song>> searchByBasic(@RequestParam(name = "keyword", required = false) String keyword) {

        List<Song> results = songService.searchBasicInfo(keyword);
        // 如果没有搜索到结果，返回404的状态码和消息
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);

    }

}
