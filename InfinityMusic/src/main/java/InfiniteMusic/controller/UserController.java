package InfiniteMusic.controller;
import InfiniteMusic.entity.User;
import InfiniteMusic.exception.UserException;
import InfiniteMusic.service.impl.PlayListServiceImpl;
import InfiniteMusic.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api (tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    PlayListServiceImpl playListService;
    @PostMapping("/login")
    public ResponseEntity<User> login( String username ,  String password) throws UserException {
        User loginuser=userService.findByUsername(username);
        if (loginuser==null){
            //throw new UserException(UserException.WRONG_USERNAME,"用户名不存在");
            return    ResponseEntity.status(UserException.WRONG_USERNAME).build();
        }
        if(password.equals(loginuser.getPassword())){
            return ResponseEntity.ok().build();
        }
        else {
          //  throw new UserException(UserException.WRONG_PASSWORD,"密码错误");
            return ResponseEntity.status(UserException.WRONG_PASSWORD).build();
        }



    }
    @PostMapping("/register")
    public ResponseEntity<User> register(String username, String password)
            throws UserException {
        if (username!=null&&username.length()>=5&&username.length()<=16
        && password!=null&&password.length()>=5&&password.length()<=16)
        {
            //查询用户
            User user=userService.findByUsername(username);
            if(user==null){
                //用户名没有被占用
                Long id = playListService.createLikeSongList();
                userService.register(username,password,id);
                return ResponseEntity.ok().build();
            }
            else{
                //被占用
                //throw new UserException(UserException.DUPLICATE_USERNAME,"重复的用户名");
                return ResponseEntity.status(UserException.DUPLICATE_USERNAME).build();
            }

        }else {
           // throw new  UserException(UserException.ILLEGALPARAM,"不合法的参数");
            return ResponseEntity.status(UserException.ILLEGALPARAM).build();
        }
    }


}
