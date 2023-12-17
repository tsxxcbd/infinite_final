package InfiniteMusic.controller;

import InfiniteMusic.entity.PlayList;
import InfiniteMusic.entity.User;
import InfiniteMusic.exception.UserInfoException;
import InfiniteMusic.service.UserInfoService;
import InfiniteMusic.service.UserService;
import InfiniteMusic.service.impl.UserInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户信息")
@RestController
@RequestMapping("user")
public class UserInfoController {
    @Autowired
    private UserInfoServiceImpl userInfoService;

    @ApiOperation("根据Id查询用户的详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@ApiParam("用户Id")@PathVariable int id) {
        User user = userInfoService.getUser(id);
        if(user==null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(user);
        }
    }

    /*@ApiOperation("根据id修改用户信息")
    @GetMapping("/1/{id}")
    public ResponseEntity<User> updateUser(@ApiParam("用户id")@PathVariable int id, @RequestBody User user) {
        int result = userInfoService.updateUser(id,user);
        if(result==0){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(user);
        }
    }*/

}
