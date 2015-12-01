package service;

import com.alibaba.fastjson.JSONArray;
import hollywood.service.RatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class RatingServiceTest {

    @Autowired
    private RatingService ratingService;

    @Test
    public void getRatingTrend() {
        JSONArray dataset = ratingService.getRatingTrendByMovieId(1);
        System.out.println(dataset);
    }

}
