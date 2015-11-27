package service;

import hollywood.pojo.Movie;
import hollywood.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void getById() {
        Movie movie = movieService.getById(1);
        assertEquals("Toy Story (1995)", movie.getTitle());
    }

}
