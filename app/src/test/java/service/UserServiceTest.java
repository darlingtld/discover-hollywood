package service;

import hollywood.pojo.Genres;
import hollywood.pojo.Movie;
import hollywood.pojo.User;
import hollywood.service.MovieService;
import hollywood.service.RatingService;
import hollywood.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @Test
    public void login() {
        String username = "lingda", password = "123";
        User user = userService.login(username, password);
        assertTrue(user != null);
    }

    @Test
    public void signup() {
        String username = "lingda1", password = "1234";
        User user = userService.signup(username, password);
        assertTrue(user != null);
    }

    @Test
    public void addFavouriteGenresList() {
        List<String> favGenresList = Arrays.asList(Genres.Action.getGenres(), Genres.Adventure.getGenres(), Genres.Comedy.getGenres());
        User user = userService.addFavouriteGenresList(20151028, favGenresList);
        assertTrue(user.getFavouriteGenresList().size() == 3);
    }

    @Test
    public void addRecommendMovieList() {
        List<Movie> recommendMovieList = movieService.getMostRatedMovies(5);
        userService.addRecommendMovieList(20151028, recommendMovieList);
        User user = userService.getByUserId(20151028);
        assertTrue(user.getFavouriteGenresList().size() == 3);
    }

    @Test
    public void getByUserId() {
        int userId = 20151128;
        assertTrue(userService.getByUserId(userId) != null);
    }

    @Test
    public void getRatedMovieList() {
        int userId = 20151128;
        List<Movie> movieList = userService.getRatedMovieList(userId);
        for (Movie movie : movieList) {
            assertEquals(movie.getAvgRating(), ratingService.getRatingByMovieIdAndUserId(movie.getMovieId(), userId).getRating());
        }
    }

}
