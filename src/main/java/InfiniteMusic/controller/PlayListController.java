package InfiniteMusic.controller;

import InfiniteMusic.auth.Result;
import InfiniteMusic.entity.*;
import InfiniteMusic.entity.dto.AddSongsDto;
import InfiniteMusic.entity.dto.PlaylistDto;
import InfiniteMusic.entity.dto.UserSongDto;
import InfiniteMusic.service.impl.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value = "/{id}")
    public Result getPlayList(@PathVariable Long id) throws Exception{

        try{
//            Long id = pl.getId();
            PlayList playList = playlistService.getPlayList(id);
//            playList.setNumber(playList_songService.finsSongsNumber(id));
//            playList.setCreatorname(userInfoService.getusername(userPlayListService.getListCreator(playList.getId())));
            return Result.ok(playList);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }


    }

    @ApiOperation("根据Id查询歌单内有哪些歌曲")
    @GetMapping(value = "/List/{id}")
    public Result getSongsinList(@PathVariable Long id) throws Exception{

        try{
//            Long id = pl.getId();
            List<Integer> songs= playList_songService.findSongsinList(id);
            List<Song> searchResults=songService.searchSong(songs);
            return Result.ok(searchResults);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @ApiOperation("新建歌单")
    @PostMapping(value = "/newlist",produces = {"application/json;charset=utf-8"})
    public Result addPlayList(@RequestBody PlaylistDto playlistDto)throws Exception{

        try{
            String name = playlistDto.getName();
            String profile = playlistDto.getProfile();
            int userid = playlistDto.getUserid();
            PlayList playList = playlistService.createPlayList(name,profile);
            userPlayListService.addPlayListCreate((long) userid,playList.getId());
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }


    }

    @ApiOperation("收藏歌单")
    @PostMapping(value = "/likelist",produces = {"application/json;charset=utf-8"})
    public Result likePlayList(@RequestBody User_PlayList user_playList)throws Exception{

        try {
            Long userid = user_playList.getId();
            Long playListId = user_playList.getId();
            userPlayListService.addPlayListLike(userid,playListId);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("删除用户创建的歌单")
    @DeleteMapping(value = "/deletelist",produces = {"application/json;charset=utf-8"})
    public Result deletePlayList(@RequestBody PlayList pl)throws Exception{

        try{
            Long id =pl.getId();
            userPlayListService.deleteCreatePlayList(id);
            playList_songService.deleteByListId(Math.toIntExact(id));
            playlistService.deletePlayList(Math.toIntExact(id));
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("删除用户喜欢的歌单")
    @DeleteMapping(value = "/deletelikelist",produces = {"application/json;charset=utf-8"})
    public Result deleteLikePlayList(@RequestBody User_PlayList user_playList)throws Exception{

        try{
            Long playlistid = user_playList.getId();
            Long userid = user_playList.getId();
            userPlayListService.deleteLikePlayList(userid,playlistid);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }


    @ApiOperation("为歌单中添加一首歌曲")
    @PostMapping(value = "/addonesong",produces = {"application/json;charset=utf-8"})
    public Result addOneSong(@RequestBody PlayList_Song playList_song)throws Exception{

        try{
            Long PlayListId = playList_song.getId();
            Long songId = playList_song.getSong_id();
            playList_songService.addOneSong(PlayListId,songId);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @ApiOperation("为歌单中添加一系列歌曲")
    @PostMapping(value = "/addmanysong",produces = {"application/json;charset=utf-8"})
    public Result addManySong(@RequestBody AddSongsDto addSongsDto)throws Exception{

        try{
            int PlayListId = addSongsDto.getPlaylistid();
            List<Integer> songIds = addSongsDto.getSongIds();
            playList_songService.addAllSong((long) PlayListId,songIds);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @ApiOperation("在歌单中去除一首歌")
    @DeleteMapping(value = "/deleteonesong",produces = {"application/json;charset=utf-8"})
    public Result deleteOneSong(@RequestBody PlayList_Song playList_song)throws Exception{

        try{
            Long PlayListId = playList_song.getId();
            Long songId = playList_song.getSong_id();
            playList_songService.deleteOneSong(Math.toIntExact(PlayListId), Math.toIntExact(songId));
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("查找用户喜欢的歌的列表")
    @GetMapping(value = "/UserlikedSong/{id}")
    public Result findLikeSongs(@PathVariable Long id)throws Exception{

        try{
//            Long userid = user.getId();
            Long likelistid = userInfoService.getlikelistid(id);
            List<Integer> songs= playList_songService.findSongsinList(likelistid);
            List<Song> searchResults=songService.searchSong(songs);
            return Result.ok(searchResults);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("查找用户创建的歌单")
    @GetMapping(value = "/UserCreatelists/{id}")
    public Result findCreateLists(@PathVariable Long id)throws Exception{

        try{
//            Long userid = user.getId();
            List<Integer> createdlist = userPlayListService.getCreateListId(id);
            List<PlayList> playLists = playlistService.getListPlayList(createdlist);
//            for(PlayList  playList : playLists){
//                playList.setNumber(playList_songService.finsSongsNumber(playList.getId()));
//                playList.setCreatorname(userInfoService.getusername(userPlayListService.getListCreator(playList.getId())));
//            }
            return Result.ok(playLists);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }


    }

    @ApiOperation("查找用户喜欢的歌单")
    @GetMapping(value = "/UserLikelists/{id}")
    public Result findLikeLists(@PathVariable Long id)throws Exception{

        try{
//            Long userid = user.getId();
            List<Integer> createdlist = userPlayListService.getLikeListId(id);
            List<PlayList> playLists = playlistService.getListPlayList(createdlist);
//            for(PlayList  playList : playLists){
//                playList.setNumber(playList_songService.finsSongsNumber(playList.getId()));
//                Long creatorid = userPlayListService.getListCreator(playList.getId());
//                String name = userInfoService.getusername(creatorid);
//                playList.setCreatorname(name);
//            }
            return Result.ok(playLists);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }


    }

    @ApiOperation("用户收藏歌单")
    @PostMapping(value = "/Likelists",produces = {"application/json;charset=utf-8"})
    public Result LikeLists(@RequestBody User_PlayList user_playList)throws Exception{

        try{
            Long userid = user_playList.getId();
            Long playlistid = user_playList.getId();
            userPlayListService.addPlayListLike(userid,playlistid);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("用户取消收藏歌单")
    @DeleteMapping(value = "/Disikelists",produces = {"application/json;charset=utf-8"})
    public Result DislikeLists(@RequestBody User_PlayList user_playList)throws Exception{

        try{
            Long userid = user_playList.getId();
            Long playlistid = user_playList.getId();
            userPlayListService.deleteLikePlayList(userid,playlistid);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("用户喜欢歌曲")
    @PostMapping(value = "/Likesong",produces = {"application/json;charset=utf-8"})
    public Result Likesong(@RequestBody UserSongDto userSongDto)throws Exception{

        try{
            int userid = userSongDto.getUserid();
            int songid = userSongDto.getSongid();
            int songlistid = Math.toIntExact(userInfoService.getlikelistid((long) userid));
            playList_songService.addOneSong((long) songlistid, (long) songid);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("用户取消喜欢歌曲")
    @DeleteMapping(value = "/Dislikesong",produces = {"application/json;charset=utf-8"})
    public Result dislikesong(@RequestBody UserSongDto userSongDto)throws Exception{

        try{
            int userid = userSongDto.getUserid();
            int songid = userSongDto.getSongid();
            int songlistid = Math.toIntExact(userInfoService.getlikelistid((long) userid));
            playList_songService.deleteOneSong(songlistid,songid);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }


}