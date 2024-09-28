package InfiniteMusic.service.impl;

import InfiniteMusic.entity.Record;
import InfiniteMusic.dao.RecordDao;
import InfiniteMusic.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordDao, Record> implements RecordService {

    @Autowired
    RecordDao recordDao;

    @Transactional
    public Record createRecord(int song_id, Data time){
        Record record = new Record();
        record.setSongid(song_id);
        record.setTime(time);
        recordDao.insert(record);
        return record;
    }
}
