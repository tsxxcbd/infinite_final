package InfiniteMusic.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddSongsDto {
    private int playlistid;
    private List<Integer> songIds;
}
