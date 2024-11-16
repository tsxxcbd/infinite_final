package InfiniteMusic.controller;

import InfiniteMusic.auth.Result;
import InfiniteMusic.auth.ResultCodeEnum;
import InfiniteMusic.dao.PlayListDao;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.Song;
import InfiniteMusic.entity.dto.SearchDto;
import InfiniteMusic.entity.vo.PlayListsVo;
import InfiniteMusic.exception.InfiniteException;
import InfiniteMusic.service.impl.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static com.mongodb.client.model.Filters.regex;
@Api(tags = "搜索管理")
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    PlayListDao playListdao;
    @Autowired
    SongServiceImpl songService;
    @Autowired
    PlayList_SongServiceImpl playList_songService;
    @Autowired
    AlbumServiceImpl albumService;
    @Autowired
    MusicianServiceImpl musicianService;
    @Autowired
    PlayListServiceImpl playListService;
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    User_PlayListServiceImpl userPlayListService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("总体查询歌曲")
    @GetMapping(value = "/{word}")
    public Result searchResults(@PathVariable String word) throws Exception{
        String key = "searchResults_" + word;
        List<Song> results = (List<Song>) redisTemplate.opsForValue().get(key);
        if(results != null){
            //如果存在，直接返回，无须查询数据库
            return Result.ok(results);
        }
        try{
            String keyword = word;
            results = songService.searchAll(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
//                return JsonResponse.Fail("NO_SEARCH_RESULT");
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌名查询歌曲")
    @GetMapping(value = "/songName/{word}")
    public Result searchBySongNme(@PathVariable String word)throws Exception {
        String key = "searchBySongNme_" + word;
        List<Song> results = (List<Song>) redisTemplate.opsForValue().get(key);
        if(results != null){
            //如果存在，直接返回，无须查询数据库
            return Result.ok(results);
        }
        try{
            String keyword = word;
            results = songService.searchBySongName(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌手查询歌曲")
    @GetMapping(value = "/artist/{word}")
    public Result searchByArtist(@PathVariable String word) throws Exception{
        String key = "searchByArtist_" + word;
        List<Song> results = (List<Song>) redisTemplate.opsForValue().get(key);
        if(results != null){
            //如果存在，直接返回，无须查询数据库
            return Result.ok(results);
        }
        try{
            String keyword = word;
            results = songService.searchByArtist(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据专辑查询歌曲")
    @GetMapping(value = "/album/{word}")
    public Result searchByAlbum(@PathVariable String word) throws Exception{
        String key = "searchByAlbum_" + word;
        List<Song> results = (List<Song>) redisTemplate.opsForValue().get(key);
        if(results != null){
            //如果存在，直接返回，无须查询数据库
            return Result.ok(results);
        }
        try{
            String keyword = word;
            results = songService.searchByAlbum(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据心情查询歌曲")
    @GetMapping(value = "/emotion/{word}")
    public Result searchByEmotion(@PathVariable String word) throws Exception{
        String key = "searchByEmotion_" + word;
        List<Song> results = (List<Song>) redisTemplate.opsForValue().get(key);
        if(results != null){
            return Result.ok(results);
        }
        try{
            String keyword = word;
            results = songService.searchByEmotion(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌曲基本信息查询，即歌名，歌手，专辑")
    @GetMapping(value = "/basic/{word}")
    public Result searchByBasic(@PathVariable String word)throws Exception {
        String key = "searchByBasic_" + word;
        List<Song> results = (List<Song>) redisTemplate.opsForValue().get(key);
        if(results != null){
            return Result.ok(results);
        }
        try{
            String keyword = word;
            results = songService.searchBasicInfo(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌单简介查询歌单")
    @GetMapping(value = "/profile/{profile}")
    public Result searchListByProfile(@PathVariable String profile)throws Exception {
        String key = "searchListByProfile_" + profile;
        List<PlayList> results = (List<PlayList>) redisTemplate.opsForValue().get(key);
        if(results != null){
            return Result.ok(results);
        }
        try{
//            String Profile = playList.getProfile();
            results = playListService.searchListByProfile(profile);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            for(PlayList playList:results){
                playList.setNumber(playList_songService.finsSongsNumber(playList.getId()));
                Long userid=userPlayListService.getListCreator(playList.getId());
                playList.setCreatorname(userInfoService.getusername(userid));
                playListdao.updateById(playList);
            }
            redisTemplate.opsForValue().set(key, results);
            return Result.ok(results);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌单名字查询歌单")
    @GetMapping(value = "/listname/{name}")
    public Result searchListByName(@PathVariable String name)throws Exception {
        String key = "searchListByName_" + name;
        List<PlayList> results = (List<PlayList>) redisTemplate.opsForValue().get(key);
        if(results != null){
            return Result.ok(results);
        }
        try{
//            String name = playList.getName();
            results = playListService.searchListByName(name);
            for(PlayList playList:results){
                playList.setNumber(playList_songService.finsSongsNumber(playList.getId()));
                Long userid=userPlayListService.getListCreator(playList.getId());
                playList.setCreatorname(userInfoService.getusername(userid));
                playListdao.updateById(playList);
            }
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                throw new InfiniteException(ResultCodeEnum.NO_SEARCH_RESULT);
            }
            redisTemplate.opsForValue().set(key, results);
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
        String key = "searchByLyrics_" + word;
        List<Document> results = (List<Document>) redisTemplate.opsForValue().get(key);
        if(results != null){
            return Result.ok(results);
        }
        // 获取Mongo数据库
        MongoDatabase database = mongoClient.getDatabase("SONGLIST");
        // 获取SONGLIST文档集合
        MongoCollection<Document> collection = database.getCollection("SONGLIST");

        // 使用正则表达式查找包含指定歌词的歌曲
        List<Document> songlist = collection.find(regex("lyrics", ".*" + word + ".*")).into(new ArrayList<>());

        redisTemplate.opsForValue().set(key, songlist);
        // 返回查询结果
        return Result.ok(songlist);
    }

}