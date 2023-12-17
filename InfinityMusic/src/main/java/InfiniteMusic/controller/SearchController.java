package InfiniteMusic.controller;
import InfiniteMusic.exception.UserException;
import InfiniteMusic.service.SearchService;
import InfiniteMusic.service.impl.SearchServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Data
//总体是基于mongodb自带的正则表达式进行匹配，对于英语效果良好，汉语可能差强人意
@RestController
@RequestMapping
public class SearchController {
    @Autowired
    private SearchServiceImpl searchService=new SearchServiceImpl();

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<List<SearchService.SearchResult>> searchResults(@RequestParam(name = "keyword", required = false) String keyword) {
        List<SearchService.SearchResult> results = searchService.search(keyword);

        // 如果没有搜索到结果
        if (results.isEmpty()) {
            return ResponseEntity.status(UserException.NO_SEARCH_RESULT).build();
        }

        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(results);
    }

}
