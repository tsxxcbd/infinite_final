package InfiniteMusic.service.impl;

import InfiniteMusic.dao.AlbumDao;
import InfiniteMusic.entity.Album;

import InfiniteMusic.service.AlbumService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumDao, Album> implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    @Transactional
    public int addAlbum(String name ,int musician_id)throws Exception {

        try {
            if (findbynameid(name, musician_id) == null) {

                Album album = new Album();
                album.setName(name);
                album.setMusicianid(musician_id);
                albumDao.insert(album);
                return album.getAlbumid();

            } else {

                return findbynameid(name, musician_id).getAlbumid();

            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }


    }

    public Album findbynameid(String name,int musician_id) throws Exception{

        LambdaQueryWrapper<Album> lqw = new LambdaQueryWrapper<Album>();
        lqw.eq(Album::getName,name).eq(Album::getMusicianid,musician_id);
        Album album = albumDao.selectOne(lqw);
        if(album!=null){
            return album;
        }else{
            throw new Exception("该专辑信息不存在");
        }
    }

}