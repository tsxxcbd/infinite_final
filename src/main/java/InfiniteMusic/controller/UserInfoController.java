package InfiniteMusic.controller;

import InfiniteMusic.entity.User;
import InfiniteMusic.result.JsonResponse;
import InfiniteMusic.service.impl.UserInfoServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户信息")
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserInfoServiceImpl userInfoService;

    @ApiOperation("根据Id查询用户的详细信息")
    @GetMapping(value = "",produces = {"application/json;charset=utf-8"})
    public String getUser(@RequestBody User userdto)throws Exception {

        try {
            int id = userdto.getUserid();
            User user = userInfoService.getUser(id);
            return JsonResponse.OK(user);
        }catch (Exception e) {
            return JsonResponse.Fail(e.getMessage());
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
