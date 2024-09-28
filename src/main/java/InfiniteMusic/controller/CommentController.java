package InfiniteMusic.controller;

import InfiniteMusic.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "评论管理")
@RestController
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;


}
