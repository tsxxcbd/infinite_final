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
    public int addAlbum(String name ,int musician_id){

        if(findbynameid(name,musician_id)==null){

            Album album = new Album();
            album.setName(name);
            album.setMusician_id(musician_id);
            albumDao.insert(album);
            return album.getAlbumid();

        }else{

            return findbynameid(name,musician_id).getAlbumid();

        }

    }

    public Album findbynameid(String name,int musician_id){

        LambdaQueryWrapper<Album> lqw = new LambdaQueryWrapper<Album>();
        lqw.eq(Album::getName,name).eq(Album::getMusician_id,musician_id);
        return albumDao.selectOne(lqw);
    }

}
