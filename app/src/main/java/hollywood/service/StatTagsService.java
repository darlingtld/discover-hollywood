package hollywood.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import hollywood.dao.MovieDao;
import hollywood.dao.StatTagsDao;
import hollywood.dao.TagDao;
import hollywood.dao.UserDao;
import hollywood.pojo.Movie;
import hollywood.pojo.StatTags;
import hollywood.pojo.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class StatTagsService {
    private Logger logger = LoggerFactory.getLogger(StatTagsService.class);

    @Autowired
    private StatTagsDao statTagsDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void save(Tag tag) {
//        save this tags to the whole tags table
        tagDao.save(tag);
//        save this tags to user tagged movie list
        Movie movie = movieDao.getById(tag.getMovieId());
        movie.setTags(tag.getTag());
        userDao.addTaggedMovieList(tag.getUserId(), movie);
    }

    @Transactional
    public int getMaxId() {
        return tagDao.getMaxId();
    }

    /**
     * main function to process tags.  combine different tags together
     *
     * @param lastId
     * @return
     */
    @Transactional
    public int processTags(int lastId) {
        logger.info("process tags. starting from last id {}", lastId);
        int id = lastId;
//        the step count
        int limit = 500;
        HashMap<Integer, StatTags> statTagsHashMap = new HashMap<>();
        List<Tag> tags = tagDao.getTagsByIdRange(id, id + limit);
        for (Tag tag : tags) {
            if (statTagsHashMap.containsKey(tag.getMovieId())) {
                StatTags statTags = statTagsHashMap.get(tag.getMovieId());
                if (!statTags.getTags().contains(tag.getTag())) {
                    statTags.setTags(statTags.getTags() + "," + tag.getTag() + ",");
                }
                statTags.setCount(statTags.getCount() + 1);
            } else {
                StatTags statTags = new StatTags();
                statTags.setMovieId(tag.getMovieId());
                statTags.setTags(tag.getTag());
                statTags.setCount(1);
                statTagsHashMap.put(tag.getMovieId(), statTags);
            }

        }
        for (Integer key : statTagsHashMap.keySet()) {
            StatTags statTags = statTagsHashMap.get(key);
//                check whether the stat tag for this movie exits
            StatTags statTagsInDB = statTagsDao.getStatTagsByMovieId(statTags.getMovieId());
            if (statTagsInDB != null) {
                statTagsInDB.setCount(statTagsInDB.getCount() + statTags.getCount());
                HashSet<String> tagSet = new HashSet<>();
                tagSet.addAll(Sets.newHashSet(statTagsInDB.getTags().split(",")));
                tagSet.addAll(Sets.newHashSet(statTags.getTags().split(",")));
                StringBuffer tagBuffer = new StringBuffer();
                for (String s : tagSet) {
                    tagBuffer.append(s).append(",");
                }
                statTagsInDB.setTags(tagBuffer.toString());
                statTagsDao.update(statTagsInDB);
            } else {
                statTagsDao.save(statTags);
            }
        }

        return tags.get(tags.size() - 1).getId();
    }

    public Tag getTagByMovieIdAndUserId(int movieId, int userId) {
        logger.info("get tag by movie id {} and user id {}", movieId, userId);
        return tagDao.getTagsByMovieIdAndUserId(movieId, userId);
    }

    @Transactional
    public JSONArray getMovieTags(int movieId) {
        logger.info("get movie tags");
        StatTags statTags = statTagsDao.getStatTagsByMovieId(movieId);
        JSONArray tagCloud = new JSONArray();
        if (statTags == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", "no tags");
            jsonObject.put("weight", 1);
            tagCloud.add(jsonObject);
            return tagCloud;
        }
        String[] tags = statTags.getTags().split(",");
        for (int i = 0; i < tags.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", tags[i]);
            jsonObject.put("weight", tags.length - i);
            tagCloud.add(jsonObject);
        }
        return tagCloud;
    }
}
