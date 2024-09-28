package InfiniteMusic.controller;

import InfiniteMusic.entity.*;
import InfiniteMusic.entity.dto.AddSongsDto;
import InfiniteMusic.entity.dto.PlaylistDto;
import InfiniteMusic.entity.dto.UserSongDto;
import InfiniteMusic.result.JsonResponse;
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
    @GetMapping(value = "",produces = {"application/json;charset=utf-8"})
    public String getPlayList(@RequestBody PlayList pl) throws Exception{

        try{
            int id = pl.getPlaylistid();
            PlayList playList = playlistService.getPlayList(id);
            playList.setNumber(playList_songService.finsSongsNumber(id));
            playList.setCreatorname(userInfoService.getusername(userPlayListService.getListCreator(playList.getPlaylistid())));
            return JsonResponse.OK(playList);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }


    }

    @ApiOperation("根据Id查询歌单内有哪些歌曲")
    @GetMapping(value = "/List", produces = {"application/json;charset=utf-8"})
    public String getSongsinList(@RequestBody PlayList pl) throws Exception{

        try{
            int id = pl.getPlaylistid();
            List<Integer> songs= playList_songService.findSongsinList(id);
            List<Song> searchResults=songService.searchSong(songs);
            return JsonResponse.OK(searchResults);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }
    }

    @ApiOperation("新建歌单")
    @PostMapping(value = "/newlist",produces = {"application/json;charset=utf-8"})
    public String addPlayList(@RequestBody PlaylistDto playlistDto)throws Exception{

        try{
            String name = playlistDto.getName();
            String profile = playlistDto.getProfile();
            int userid = playlistDto.getUserid();
            PlayList playList = playlistService.createPlayList(name,profile);
            userPlayListService.addPlayListCreate(userid,playList.getPlaylistid());
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }


    }

    @ApiOperation("收藏歌单")
    @PostMapping(value = "/likelist",produces = {"application/json;charset=utf-8"})
    public String likePlayList(@RequestBody User_PlayList user_playList)throws Exception{

        try {
            int userid = user_playList.getUserid();
            int playListId = user_playList.getPlaylistid();
            userPlayListService.addPlayListLike(userid,playListId);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("删除用户创建的歌单")
    @DeleteMapping(value = "/deletelist",produces = {"application/json;charset=utf-8"})
    public String deletePlayList(@RequestBody PlayList pl)throws Exception{

        try{
            int id =pl.getPlaylistid();
            userPlayListService.deleteCreatePlayList(id);
            playList_songService.deleteByListId(id);
            playlistService.deletePlayList(id);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("删除用户喜欢的歌单")
    @DeleteMapping(value = "/deletelikelist",produces = {"application/json;charset=utf-8"})
    public String deleteLikePlayList(@RequestBody User_PlayList user_playList)throws Exception{

        try{
            int playlistid = user_playList.getPlaylistid();
            int userid = user_playList.getUserid();
            userPlayListService.deleteLikePlayList(userid,playlistid);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }
    }


    @ApiOperation("为歌单中添加一首歌曲")
    @PostMapping(value = "/addonesong",produces = {"application/json;charset=utf-8"})
    public String addOneSong(@RequestBody PlayList_Song playList_song)throws Exception{

        try{
            int PlayListId = playList_song.getPlaylistid();
            int songId = playList_song.getSongid();
            playList_songService.addOneSong(PlayListId,songId);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }
    }

    @ApiOperation("为歌单中添加一系列歌曲")
    @PostMapping(value = "/addmanysong",produces = {"application/json;charset=utf-8"})
    public String addManySong(@RequestBody AddSongsDto addSongsDto)throws Exception{

        try{
            int PlayListId = addSongsDto.getPlaylistid();
            List<Integer> songIds = addSongsDto.getSongIds();
            playList_songService.addAllSong(PlayListId,songIds);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }
    }

    @ApiOperation("在歌单中去除一首歌")
    @DeleteMapping(value = "/deleteonesong",produces = {"application/json;charset=utf-8"})
    public String deleteOneSong(@RequestBody PlayList_Song playList_song)throws Exception{

        try{
            int PlayListId = playList_song.getPlaylistid();
            int songId = playList_song.getSongid();
            playList_songService.deleteOneSong(PlayListId,songId);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("查找用户喜欢的歌的列表")
    @GetMapping(value = "/UserlikedSong",produces = {"application/json;charset=utf-8"})
    public String findLikeSongs(@RequestBody User user)throws Exception{

        try{
            int userid = user.getUserid();
            int likelistid = userInfoService.getlikelistid(userid);
            List<Integer> songs= playList_songService.findSongsinList(likelistid);
            List<Song> searchResults=songService.searchSong(songs);
            return JsonResponse.OK(searchResults);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("查找用户创建的歌单")
    @GetMapping(value = "/UserCreatelists",produces = {"application/json;charset=utf-8"})
    public String findCreateLists(@RequestBody User user)throws Exception{

        try{
            int userid = user.getUserid();
            List<Integer> createdlist = userPlayListService.getCreateListId(userid);
            List<PlayList> playLists = playlistService.getListPlayList(createdlist);
            for(PlayList  playList : playLists){
                playList.setNumber(playList_songService.finsSongsNumber(playList.getPlaylistid()));
                playList.setCreatorname(userInfoService.getusername(userPlayListService.getListCreator(playList.getPlaylistid())));
            }
            return JsonResponse.OK(playLists);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }


    }

    @ApiOperation("查找用户喜欢的歌单")
    @GetMapping(value = "/UserLikelists",produces = {"application/json;charset=utf-8"})
    public String findLikeLists(@RequestBody User user)throws Exception{

        try{
            int userid = user.getUserid();
            List<Integer> createdlist = userPlayListService.getLikeListId(userid);
            List<PlayList> playLists = playlistService.getListPlayList(createdlist);
            for(PlayList  playList : playLists){
                playList.setNumber(playList_songService.finsSongsNumber(playList.getPlaylistid()));
                int creatorid = userPlayListService.getListCreator(playList.getPlaylistid());
                String name = userInfoService.getusername(creatorid);
                playList.setCreatorname(name);
            }
            return JsonResponse.OK(playLists);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }


    }

    @ApiOperation("用户收藏歌单")
    @PostMapping(value = "/Likelists",produces = {"application/json;charset=utf-8"})
    public String LikeLists(@RequestBody User_PlayList user_playList)throws Exception{

        try{
            int userid = user_playList.getUserid();
            int playlistid = user_playList.getPlaylistid();
            userPlayListService.addPlayListLike(userid,playlistid);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("用户取消收藏歌单")
    @DeleteMapping(value = "/Disikelists",produces = {"application/json;charset=utf-8"})
    public String DislikeLists(@RequestBody User_PlayList user_playList)throws Exception{

        try{
            int userid = user_playList.getUserid();
            int playlistid = user_playList.getPlaylistid();
            userPlayListService.deleteLikePlayList(userid,playlistid);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("用户喜欢歌曲")
    @PostMapping(value = "/Likesong",produces = {"application/json;charset=utf-8"})
    public String Likesong(@RequestBody UserSongDto userSongDto)throws Exception{

        try{
            int userid = userSongDto.getUserid();
            int songid = userSongDto.getSongid();
            int songlistid = userInfoService.getlikelistid(userid);
            playList_songService.addOneSong(songlistid,songid);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("用户取消喜欢歌曲")
    @DeleteMapping(value = "/Dislikesong",produces = {"application/json;charset=utf-8"})
    public String dislikesong(@RequestBody UserSongDto userSongDto)throws Exception{

        try{
            int userid = userSongDto.getUserid();
            int songid = userSongDto.getSongid();
            int songlistid = userInfoService.getlikelistid(userid);
            playList_songService.deleteOneSong(songlistid,songid);
            return JsonResponse.OK();
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }


}
