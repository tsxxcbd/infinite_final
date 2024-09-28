package InfiniteMusic.entity;

import lombok.Data;

@Data
public class Player {

    private Song song;
    private PlayList currentList;
    private boolean isPlaying;
    private boolean isPaused;
    private  int volume;
    private int seek;//音乐播放进度
    public void play(){}
    public void pause(){}

    public void setVolume() {}
    public void setSeek(){}
}
