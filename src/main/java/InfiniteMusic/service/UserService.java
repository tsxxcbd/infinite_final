package InfiniteMusic.service;

import InfiniteMusic.entity.User;
import InfiniteMusic.entity.dto.UserDto;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User>{
    void addUser(UserDto userDto);
}
