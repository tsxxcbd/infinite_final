package InfiniteMusic.controller;

import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.Song;
import InfiniteMusic.entity.dto.SongDto;
import InfiniteMusic.result.JsonResponse;
import InfiniteMusic.result.Result;
import InfiniteMusic.service.CommentService;
import InfiniteMusic.service.impl.AlbumServiceImpl;
import InfiniteMusic.service.impl.CommentServiceImpl;
import InfiniteMusic.service.impl.MusicianServiceImpl;
import InfiniteMusic.service.impl.SongServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "歌曲管理")
@RestController
@RequestMapping("/Song")
public class SongController {

    @Autowired
    SongServiceImpl songService;
    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    MusicianServiceImpl  musicianService;

    @ApiOperation("将歌曲信息存入数据库")
    @PostMapping(value = "/new",produces = {"application/json;charset=utf-8"})
    public String getSong(@RequestBody SongDto songDto)throws Exception {

        try {
            int song_id = songDto.getSong_id();
            String artist = songDto.getArtist();
            String album = songDto.getAlbum();
            String song_name = songDto.getSong_name();
            String emotion = songDto.getEmotion();

            int musician_id = musicianService.addMusician(artist);
            int album_id = albumService.addAlbum(album, musician_id);
            Song song = songService.CreatSong(song_id, song_name, emotion, album_id, musician_id, album, artist);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }
    }


}
