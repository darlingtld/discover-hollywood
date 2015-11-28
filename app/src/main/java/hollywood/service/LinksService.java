package hollywood.service;

import hollywood.dao.LinksDao;
import hollywood.pojo.Links;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class LinksService {
    private static final Logger logger = LoggerFactory.getLogger(LinksService.class);
    @Autowired
    private LinksDao linksDao;

    @Transactional
    public void save(Links links){
        logger.info("save links {}", links);
        linksDao.save(links);
    }
}
