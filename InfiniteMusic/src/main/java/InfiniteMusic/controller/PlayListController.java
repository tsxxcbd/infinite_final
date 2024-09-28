package InfiniteMusic.controller;

import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.entity.Song;
import InfiniteMusic.exception.PlayListException;
import InfiniteMusic.result.Result;
import InfiniteMusic.service.PlayList_SongService;
import InfiniteMusic.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import InfiniteMusic.service.impl.PlayList_SongServiceImpl;
import java.util.List;

@Api(tags = "歌单管理")
@RestController
@RequestMapping("/PlayList")
public class PlayListController {

    @Autowired
    PlayListServiceImpl playlistService ;
    @Autowired
    PlayList_SongServiceImpl playList_songService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    User_PlayListServiceImpl userPlayListService;

    @Autowired
    SongServiceImpl songService;

    //所有的api注解非必要写，能够自己辨认清楚就不用写了
    @ApiOperation("根据Id查询歌单的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<PlayList> getPlayList(@ApiParam("歌单Id")@PathVariable int id) {
        PlayList playList = playlistService.getPlayList(id);
        playList.setNumber(playList_songService.finsSongsNumber(id));
        playList.setCreatorname(userInfoService.getusername(userPlayListService.getListCreator(playList.getId())));
        if(playList==null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(playList);
        }
    }

    @ApiOperation("根据Id查询歌单内有哪些歌曲")
    @GetMapping("/List/{id}")
    public ResponseEntity<List<Song>> getSongsinList(@ApiParam("歌单Id")@PathVariable int id) {
        List<Integer> songs= playList_songService.findSongsinList(id);
        List<Song> searchResults=songService.searchSong(songs);
        if(searchResults==null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(searchResults);
        }
    }

    @ApiOperation("新建歌单")
    @PostMapping("/newlist")
    public ResponseEntity<Void> addPlayList(@RequestParam String name,@RequestParam String profile,@RequestParam int userid){
        PlayList playList = playlistService.createPlayList(name,profile);
        userPlayListService.addPlayListCreate(userid,playList.getId());
        return ResponseEntity.ok().build();
    }

    @ApiOperation("收藏歌单")
    @PostMapping("/likelist")
    public ResponseEntity<Void> likePlayList(@RequestParam int playListId,@RequestParam int userid){
        userPlayListService.addPlayListLike(userid,playListId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除创建的歌单")
    @DeleteMapping("/deletelist")
    public ResponseEntity<Void> deletePlayList(@RequestParam int id){
        userPlayListService.deleteCreatePlayList(id);
        playList_songService.deleteByListId(id);
        playlistService.deletePlayList(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除喜欢的歌单")
    @DeleteMapping("/deletelikelist")
    public ResponseEntity<Void> deleteLikePlayList(@RequestParam int id){
        userPlayListService.deleteLikePlayList(id);
        return ResponseEntity.ok().build();
    }


    @ApiOperation("为歌单中添加一首歌曲")
    @PostMapping("/addonesong")
    public ResponseEntity<Void> addOneSong(@RequestParam int PlayListId ,@RequestParam int songId){
        playList_songService.addOneSong(PlayListId,songId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("为歌单中添加一系列歌曲")
    @PostMapping("/addmanysong")
    public Result addManySong(@RequestParam int PlayListId , @RequestParam List<Integer> songIds){
        try{
            playList_songService.addAllSong(PlayListId,songIds);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @ApiOperation("在歌单中去除一首歌")
    @DeleteMapping("deleteonesong")
    public ResponseEntity<Void> deleteOneSong(@RequestParam int PlayListId ,@RequestParam int songId){
        playList_songService.deleteOneSong(PlayListId,songId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("查找用户喜欢的歌的列表")
    @GetMapping("/UserlikedSong")
    public ResponseEntity<List<Song>> findLikeSongs(@RequestParam int userid){
        int likelistid = userInfoService.getlikelistid(userid);
        List<Integer> songs= playList_songService.findSongsinList(likelistid);
        List<Song> searchResults=songService.searchSong(songs);
        return ResponseEntity.ok(searchResults);
    }

    @ApiOperation("查找用户创建的歌单")
    @GetMapping("/UserCreatelists")
    public List<PlayList> findCreateLists(@RequestParam int userid){
        List<Integer> createdlist = userPlayListService.getCreateListId(userid);
        List<PlayList> playLists = playlistService.getListPlayList(createdlist);
        for(PlayList  playList : playLists){
            playList.setNumber(playList_songService.finsSongsNumber(playList.getId()));
            playList.setCreatorname(userInfoService.getusername(userPlayListService.getListCreator(playList.getId())));
        }
        return playLists;

    }

    @ApiOperation("查找用户喜欢的歌单")
    @GetMapping("/UserLikelists")
    public ResponseEntity<List<PlayList>> findLikeLists(@RequestParam int userid){
        List<Integer> createdlist = userPlayListService.getLikeListId(userid);
        List<PlayList> playLists = playlistService.getListPlayList(createdlist);
        for(PlayList  playList : playLists){
            playList.setNumber(playList_songService.finsSongsNumber(playList.getId()));
            int creatorid = userPlayListService.getListCreator(playList.getId());
            String name = userInfoService.getusername(creatorid);
            playList.setCreatorname(name);
        }
        return ResponseEntity.ok(playLists);

    }


}
