package InfiniteMusic.service.impl;

import InfiniteMusic.dao.MusicianDao;
import InfiniteMusic.entity.Musician;
import InfiniteMusic.entity.PlayList_Song;
import InfiniteMusic.service.MusicianService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MusicianServiceImpl extends ServiceImpl<MusicianDao, Musician> implements MusicianService {

    @Autowired
    MusicianDao musicianDao;

    //添加歌手，返回歌手的id
    @Transactional
    public int addMusician(String name){
        if( findbyname(name) == null ){
            Musician musician = new Musician();
            musician.setName(name);
            musicianDao.insert(musician);
            return musician.getId();
        }else{
            return findbyname(name).getId();
        }
    }

    public Musician findbyname(String name){
        LambdaQueryWrapper<Musician> lqw = new LambdaQueryWrapper<Musician>();
        lqw.eq(Musician::getName,name);
        return musicianDao.selectOne(lqw);
    }
}
