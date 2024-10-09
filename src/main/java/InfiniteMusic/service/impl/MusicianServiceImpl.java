package InfiniteMusic.service.impl;

import InfiniteMusic.dao.MusicianDao;
import InfiniteMusic.entity.Musician;
import InfiniteMusic.service.MusicianService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicianServiceImpl extends ServiceImpl<MusicianDao, Musician> implements MusicianService {

    @Autowired
    MusicianDao musicianDao;

    //添加歌手，返回歌手的id
    @Transactional
    public int addMusician(String name)throws Exception{
        try{
            if( findbyname(name) == null ){
                Musician musician = new Musician();
                musician.setName(name);
                musicianDao.insert(musician);
                return musician.getMusicianid();
            }else{
                return findbyname(name).getMusicianid();
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Musician findbyname(String name)throws Exception{
        LambdaQueryWrapper<Musician> lqw = new LambdaQueryWrapper<Musician>();
        lqw.eq(Musician::getName,name);
        Musician musician = musicianDao.selectOne(lqw);
        if(musician!=null){
            return musician;
        }else{
            throw new Exception("该歌手信息不存在");
        }
    }
}