package InfiniteMusic.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserLoginVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String token;
}
