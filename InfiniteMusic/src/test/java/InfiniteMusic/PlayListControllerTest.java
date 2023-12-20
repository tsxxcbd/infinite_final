package InfiniteMusic;

import InfiniteMusic.controller.PlayListController;
import InfiniteMusic.entity.PlayList;
import InfiniteMusic.service.impl.PlayListServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 使用MockMVC对Controller进行测试
 */
@SpringBootTest(classes = PlayListController.class)
@AutoConfigureMockMvc
public class PlayListControllerTest {

   /* @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayListServiceImpl playlistService;

/*    @Test
    public void getPlayListTest() throws Exception {
        this.mockMvc.perform(get("/PlayList/{id}",1))
                .andDo(print()) //控制台打印出返回消息
                .andExpect(status().isNoContent());

        playlistService.getPlayList(1L);

        this.mockMvc.perform(get("/todos/{id}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isNoContent());
    }*/
    @Test
    public void PlaylistService(){
        PlayListServiceImpl pi = new PlayListServiceImpl();
        PlayList pl = pi.getPlayList(1L);
        System.out.print("1");
    }
}
