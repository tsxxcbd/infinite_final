package InfiniteMusic.controller;

import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.exception.PlayListException;
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
    PlayList_SongServiceImpl playList_songService=new PlayList_SongServiceImpl();
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    User_PlayListServiceImpl userPlayListService;

    //所有的api注解非必要写，能够自己辨认清楚就不用写了
    @ApiOperation("根据Id查询歌单的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<PlayList> getPlayList(@ApiParam("歌单Id")@PathVariable Long id) {
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
    public ResponseEntity<List<PlayList_SongService.listSongSearchResult>> getSongsinList(@ApiParam("歌单Id")@PathVariable Long id) {
        List<Long> songs= playList_songService.findSongsinList(id);
        List<PlayList_SongService.listSongSearchResult> searchResults=playList_songService.searchSong(songs);
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
    public ResponseEntity<Void> likePlayList(@RequestParam Long playListId,@RequestParam int userid){
        userPlayListService.addPlayListLike(userid,playListId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除创建的歌单")
    @DeleteMapping("/deletelist")
    public ResponseEntity<Void> deletePlayList(@RequestParam Long id){
        userPlayListService.deleteCreatePlayList(id);
        playList_songService.deleteByListId(id);
        playlistService.deletePlayList(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("删除喜欢的歌单")
    @DeleteMapping("/deletelikelist")
    public ResponseEntity<Void> deleteLikePlayList(@RequestParam Long id){
        userPlayListService.deleteLikePlayList(id);
        return ResponseEntity.ok().build();
    }


    @ApiOperation("为歌单中添加一首歌曲")
    @PostMapping("/addonesong")
    public ResponseEntity<Void> addOneSong(@RequestParam Long PlayListId ,@RequestParam Long songId){
        playList_songService.addOneSong(PlayListId,songId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("为歌单中添加一系列歌曲")
    @PostMapping("/addmanysong")
    public ResponseEntity<Void> addManySong(@RequestParam Long PlayListId ,@RequestParam List<Long> songIds){
        playList_songService.addAllSong(PlayListId,songIds);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("在歌单中去除一首歌")
    @DeleteMapping("deleteonesong")
    public ResponseEntity<Void> deleteOneSong(@RequestParam Long PlayListId ,@RequestParam Long songId){
        playList_songService.deleteOneSong(PlayListId,songId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("查找用户喜欢的歌的列表")
    @GetMapping("/UserlikedSong")
    public ResponseEntity<List<PlayList_SongService.listSongSearchResult>> findLikeSongs(@RequestParam int userid){
        Long likelistid = userInfoService.getlikelistid(userid);
        List<Long> songs= playList_songService.findSongsinList(likelistid);
        List<PlayList_SongService.listSongSearchResult> searchResults=playList_songService.searchSong(songs);
        return ResponseEntity.ok(searchResults);
    }

    @ApiOperation("查找用户创建的歌单")
    @GetMapping("/UserCreatelists")
    public List<PlayList> findCreateLists(@RequestParam int userid){
        List<Long> createdlist = userPlayListService.getCreateListId(userid);
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
        List<Long> createdlist = userPlayListService.getLikeListId(userid);
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
