package InfiniteMusic.controller;

import InfiniteMusic.auth.Result;
import InfiniteMusic.auth.ResultCodeEnum;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.Song;
import InfiniteMusic.entity.dto.SearchDto;
import InfiniteMusic.exception.InfiniteException;
import InfiniteMusic.service.impl.AlbumServiceImpl;
import InfiniteMusic.service.impl.MusicianServiceImpl;
import InfiniteMusic.service.impl.PlayListServiceImpl;
import InfiniteMusic.service.impl.SongServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "搜索管理")
@RestController
@RequestMapping("/search")
public class SearchController {


    @Autowired
    SongServiceImpl songService;
    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    MusicianServiceImpl musicianService;
    @Autowired
    PlayListServiceImpl playListService;


    @ApiOperation("总体查询歌曲")
    @GetMapping(value = "",produces = {"application/json;charset=utf-8"})
    public Result searchResults(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchAll(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
//                return JsonResponse.Fail("NO_SEARCH_RESULT");
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌名查询歌曲")
    @GetMapping(value = "/songName",produces = {"application/json;charset=utf-8"})
    public Result searchBySongNme(@RequestBody(required = false) SearchDto word)throws Exception {

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchBySongName(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌手查询歌曲")
    @GetMapping(value = "/artist",produces = {"application/json;charset=utf-8"})
    public Result searchByArtist(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchByArtist(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据专辑查询歌曲")
    @GetMapping(value = "/album",produces = {"application/json;charset=utf-8"})
    public Result searchByAlbum(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchByAlbum(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据心情查询歌曲")
    @GetMapping(value = "/emotion",produces = {"application/json;charset=utf-8"})
    public Result searchByEmotion(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchByEmotion(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌曲基本信息查询，即歌名，歌手，专辑")
    @GetMapping(value = "/basic",produces = {"application/json;charset=utf-8"})
    public Result searchByBasic(@RequestBody(required = false) SearchDto word)throws Exception {

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchBasicInfo(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌单简介查询歌单")
    @GetMapping(value = "/profile",produces = {"application/json;charset=utf-8"})
    public Result searchListByProfile(@RequestBody PlayList playList)throws Exception {

        try{
            String Profile = playList.getProfile();
            List<PlayList> results = playListService.searchListByProfile(Profile);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌单名字查询歌单")
    @GetMapping(value = "/listname",produces = {"application/json;charset=utf-8"})
    public Result searchListByName(@RequestBody PlayList playList)throws Exception {

        try{
            String name = playList.getName();
            List<PlayList> results = playListService.searchListByName(name);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

}