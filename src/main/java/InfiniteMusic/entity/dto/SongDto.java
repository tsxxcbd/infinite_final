package InfiniteMusic.entity.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class SongDto {
    private int song_id;
    private String song_name;
    private String emotion;
    private String artist;
    private String album;
}
