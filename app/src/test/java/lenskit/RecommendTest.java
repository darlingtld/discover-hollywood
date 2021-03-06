package lenskit;

import hollywood.engine.MovieRecommender;
import hollywood.pojo.User;
import hollywood.service.UserService;
import org.grouplens.lenskit.scored.ScoredId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by lingda on 2015/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class RecommendTest {

    @Autowired
    private MovieRecommender movieRecommender;

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        User user = userService.login("lingda", "123");
        List<ScoredId> scoredIdList = movieRecommender.recommend(user, 5);
        assertTrue(!scoredIdList.isEmpty());
    }
}
