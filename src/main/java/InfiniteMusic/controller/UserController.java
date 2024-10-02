package InfiniteMusic.controller;
import InfiniteMusic.auth.Result;
import InfiniteMusic.auth.ResultCodeEnum;
import InfiniteMusic.dao.UserDao;
import InfiniteMusic.entity.User;
import InfiniteMusic.entity.dto.UserDto;
import InfiniteMusic.entity.dto.UserLoginDto;
import InfiniteMusic.entity.vo.UserLoginVo;
import InfiniteMusic.exception.InfiniteException;
import InfiniteMusic.properties.JwtProperties;
import InfiniteMusic.service.UserService;
import InfiniteMusic.service.impl.PlayListServiceImpl;
import InfiniteMusic.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PlayListServiceImpl playListService;
    @Autowired
    UserDao userDao;
    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        String username = userLoginDto.getName();
        String password = userLoginDto.getPassword();
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new InfiniteException(ResultCodeEnum.WRONG_USERNAME);
        }
        //md5加密，暂未启用
//        String psd = DigestUtils.md5DigestAsHex(password.getBytes());
//        if (!psd.equals(user.getPassword())){
//            throw new InfiniteException(ResultCodeEnum.WRONG_PASSWORD);
//        }
        if (!password.equals(user.getPassword())){
            throw new InfiniteException(ResultCodeEnum.WRONG_PASSWORD);
        }
        //jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        UserLoginVo userLoginVo = UserLoginVo.builder()
                .id(user.getId())
                .name(user.getName())
                .token(token)
                .build();

        return Result.ok(userLoginVo);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) {
        String username = userDto.getName();
        String password = userDto.getPassword();
        if (username!=null&&username.length()>=5&&username.length()<=16
        && password!=null&&password.length()>=5&&password.length()<=16)
        {
            //查询用户
            User user1=userDao.findByUsername(username);
            if(user1==null){
                //用户名没有被占用
                User user = new User();
                Long likelistId = playListService.createLikeSongList();
                user.setName(username);
                //md5加密，暂未启用
                //user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
                user.setPassword(password);
                user.setLikelistId(likelistId);
                userDao.add(username,password,likelistId);
                //return ResponseEntity.ok().build();
                return Result.ok();
            }
            else{
                //名称被占用
                throw new InfiniteException(ResultCodeEnum.DUPLICATE_USERNAME);
            }

        }else {
            //参数不合法
            throw new InfiniteException(ResultCodeEnum.ILLEGALPARAM);
        }
    }


}
