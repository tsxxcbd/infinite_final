package InfiniteMusic.entity.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class PlaylistDto {

    private String name;
    private String profile;
    private int userid;
}
