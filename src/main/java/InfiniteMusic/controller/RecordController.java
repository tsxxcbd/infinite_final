package InfiniteMusic.controller;

import InfiniteMusic.service.RecordService;
import InfiniteMusic.service.impl.RecordServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "听歌记录管理")
@RestController
@RequestMapping("/Record")
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;



}
