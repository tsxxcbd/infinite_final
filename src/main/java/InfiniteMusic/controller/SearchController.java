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
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.regex;
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
    @GetMapping(value = "/{word}")
    public Result searchResults(@PathVariable String word) throws Exception{

        try{
            String keyword = word;
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
    @GetMapping(value = "/songName/{word}")
    public Result searchBySongNme(@PathVariable String word)throws Exception {

        try{
            String keyword = word;
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
    @GetMapping(value = "/artist/{word}")
    public Result searchByArtist(@PathVariable String word) throws Exception{

        try{
            String keyword = word;
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
    @GetMapping(value = "/album/{word}")
    public Result searchByAlbum(@PathVariable String word) throws Exception{

        try{
            String keyword = word;
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
    @GetMapping(value = "/emotion/{word}")
    public Result searchByEmotion(@PathVariable String word) throws Exception{

        try{
            String keyword = word;
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
    @GetMapping(value = "/basic/{word}")
    public Result searchByBasic(@PathVariable String word)throws Exception {

        try{
            String keyword = word;
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
    @Autowired
    private MongoClient mongoClient;
    @ApiOperation("根据歌词查询歌曲")
    @GetMapping(value = "/lyrics/{word}")
    public Result searchByLyrics(@PathVariable String word)throws Exception {
        // 获取Mongo数据库
        MongoDatabase database = mongoClient.getDatabase("SONGLIST");
        // 获取SONGLIST文档集合
        MongoCollection<Document> collection = database.getCollection("SONGLIST");

        // 使用正则表达式查找包含指定歌词的歌曲
        List<Document> songlist = collection.find(regex("lyrics", ".*" + word + ".*")).into(new ArrayList<>());

        // 返回查询结果
        return Result.ok(songlist);
    }

}