package InfiniteMusic.controller;
import InfiniteMusic.auth.Result;
import InfiniteMusic.auth.ResultCodeEnum;
import InfiniteMusic.dao.UserDao;
import InfiniteMusic.entity.User;
import InfiniteMusic.entity.dto.UserDto;
import InfiniteMusic.entity.dto.UserLoginDto;
import InfiniteMusic.entity.vo.UserLoginVo;
import InfiniteMusic.exception.InfiniteException;
import InfiniteMusic.exception.UserException;
import InfiniteMusic.properties.JwtProperties;
import InfiniteMusic.service.UserService;
import InfiniteMusic.service.impl.PlayListServiceImpl;
import InfiniteMusic.service.impl.UserServiceImpl;
import InfiniteMusic.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
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
//    public ResponseEntity<User> login( String username ,  String password) throws UserException {
//        User loginuser=userService.findByUsername(username);
//        if (loginuser==null){
//            //throw new UserException(UserException.WRONG_USERNAME,"用户名不存在");
//            return    ResponseEntity.status(UserException.WRONG_USERNAME).build();
//        }
//        if(password.equals(loginuser.getPassword())){
//            return ResponseEntity.ok().build();
//        }
//        else {
//          //  throw new UserException(UserException.WRONG_PASSWORD,"密码错误");
//            return ResponseEntity.status(UserException.WRONG_PASSWORD).build();
//        }
//    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) throws Exception {
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
                //被占用
                //throw new UserException(UserException.DUPLICATE_USERNAME,"重复的用户名");
                throw new InfiniteException(ResultCodeEnum.DUPLICATE_USERNAME);
            }

        }else {
           // throw new  UserException(UserException.ILLEGALPARAM,"不合法的参数");
            throw new InfiniteException(ResultCodeEnum.ILLEGALPARAM);
        }
    }
//    @ApiOperation("add User(User Register)")
//    @PostMapping("register1")
//    public Result addUser(@RequestBody UserDto userDto){
//        userService.addUser(userDto);
//        return Result.ok();
//    }


}
