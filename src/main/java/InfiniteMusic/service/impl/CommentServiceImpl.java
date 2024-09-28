package InfiniteMusic.service.impl;

import InfiniteMusic.entity.Comment;
import InfiniteMusic.dao.CommentDao;
import InfiniteMusic.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {


}
