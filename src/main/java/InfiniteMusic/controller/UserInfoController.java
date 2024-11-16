package InfiniteMusic.controller;

import InfiniteMusic.auth.Result;
import InfiniteMusic.auth.ResultCodeEnum;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.User;
import InfiniteMusic.exception.InfiniteException;
import InfiniteMusic.exception.UserInfoException;
import InfiniteMusic.service.UserInfoService;
import InfiniteMusic.service.UserService;
import InfiniteMusic.service.impl.UserInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户信息")
@RestController
@RequestMapping("user")
public class UserInfoController {
    @Autowired
    private UserInfoServiceImpl userInfoService;
    @Autowired
    private RedisTemplate redisTemplate;
    @ApiOperation("根据Id查询用户的详细信息")
    @GetMapping("/{id}")
    public Result getUser(@ApiParam("用户Id")@PathVariable Long id) throws Exception {
        User user = userInfoService.getUser(id);
        if(user==null){
            throw new InfiniteException(ResultCodeEnum.USER_NOT_EXIST);
        }else{
            return Result.ok(user);
        }
    }
    @ApiOperation("根据id修改用户信息")
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user) {
        int result = userInfoService.updateUser(user);
        if(result==0){
            throw new InfiniteException(ResultCodeEnum.USER_NOT_EXIST);
        }else{
            return Result.ok();
        }
    }

}
