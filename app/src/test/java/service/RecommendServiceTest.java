package service;

import hollywood.pojo.Genres;
import hollywood.pojo.Movie;
import hollywood.service.RecommendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class RecommendServiceTest {

    @Autowired
    private RecommendService recommendService;

    @Test
    public void recommendByGenres() {
        List<Movie> movieList = recommendService.recommendByGenres(Arrays.asList(Genres.Comedy.getGenres()), 10);
        movieList.forEach(System.out::println);
    }

}
