package hollywood.service;

import hollywood.dao.TagDao;
import hollywood.pojo.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class TagService {
    private static final Logger logger = LoggerFactory.getLogger(TagService.class);
    @Autowired
    private TagDao tagDao;

    @Transactional
    public void save(Tag tag) {
        logger.info("save tag {}", tag);
        tagDao.save(tag);
    }

    @Transactional
    public int getMaxTagId() {
        return tagDao.getMaxId();
    }
}
