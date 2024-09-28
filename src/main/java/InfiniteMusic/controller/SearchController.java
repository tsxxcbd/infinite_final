package InfiniteMusic.controller;

import InfiniteMusic.entity.Song;
import InfiniteMusic.entity.dto.SearchDto;
import InfiniteMusic.result.JsonResponse;
import InfiniteMusic.service.impl.AlbumServiceImpl;
import InfiniteMusic.service.impl.MusicianServiceImpl;
import InfiniteMusic.service.impl.SongServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value = "",produces = {"application/json;charset=utf-8"})
    public String searchResults(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchAll(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                return JsonResponse.Fail("NO_SEARCH_RESULT");
            }
            return JsonResponse.OK(results);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌名查询")
    @GetMapping(value = "/songName",produces = {"application/json;charset=utf-8"})
    public String searchBySongNme(@RequestBody(required = false) SearchDto word)throws Exception {

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchBySongName(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                return JsonResponse.Fail("NO_SEARCH_RESULT");
            }
            return JsonResponse.OK(results);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌手查询")
    @GetMapping(value = "/artist",produces = {"application/json;charset=utf-8"})
    public String searchByArtist(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchByArtist(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                return JsonResponse.Fail("NO_SEARCH_RESULT");
            }
            return JsonResponse.OK(results);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("根据专辑查询")
    @GetMapping(value = "/album",produces = {"application/json;charset=utf-8"})
    public String searchByAlbum(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchByAlbum(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                return JsonResponse.Fail("NO_SEARCH_RESULT");
            }
            return JsonResponse.OK(results);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("根据心情查询")
    @GetMapping(value = "/emotion",produces = {"application/json;charset=utf-8"})
    public String searchByEmotion(@RequestBody(required = false) SearchDto word) throws Exception{

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchByEmotion(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                return JsonResponse.Fail("NO_SEARCH_RESULT");
            }
            return JsonResponse.OK(results);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

    @ApiOperation("根据歌曲基本信息查询，即歌名，歌手，专辑")
    @GetMapping(value = "/basic",produces = {"application/json;charset=utf-8"})
    public String searchByBasic(@RequestBody(required = false) SearchDto word)throws Exception {

        try{
            String keyword = word.getKeyword();
            List<Song> results = songService.searchBasicInfo(keyword);
            // 如果没有搜索到结果，返回404的状态码和消息
            if (results.isEmpty()) {
                return JsonResponse.Fail("NO_SEARCH_RESULT");
            }
            return JsonResponse.OK(results);
        }catch (Exception e){
            return JsonResponse.Fail(e.getMessage());
        }

    }

}
