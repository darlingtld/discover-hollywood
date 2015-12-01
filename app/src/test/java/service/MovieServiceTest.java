package service;

import hollywood.LuceneSearcher;
import hollywood.pojo.Genres;
import hollywood.pojo.Movie;
import hollywood.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tangl9 on 2015-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private LuceneSearcher luceneSearcher;

    @Test
    public void getById() {
        Movie movie = movieService.getById(1);
        assertEquals("Toy Story (1995)", movie.getTitle());
    }

    @Test
    public void save() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setGenres("Test Genres");
        movie.setMovieId(999999);
        Movie tmp = movieService.getById(999999);
        assertEquals(tmp.getTitle(), movie.getTitle());
    }

    @Test
    public void searchMoviesByTitle() throws Exception {
        String keyword = "story";
        List<Movie> movieList = luceneSearcher.searchMoviesByTitle(keyword, 5);
        for (Movie movie : movieList) {
            assertTrue(movie.getTitle().toLowerCase().contains(keyword.toLowerCase()));
        }
    }

    @Test
    public void update() {
        Movie movie = movieService.getById(999999);
        String updateStr = "Test Movie 2";
        movie.setTitle(updateStr);
        movieService.update(movie);
        assertEquals(updateStr, movieService.getById(999999).getTitle());
    }

    @Test
    public void getMissingPostUrlMovies() {
        List<Movie> movieList = movieService.getMissingPostUrlMovies(10);
        for (Movie movie : movieList) {
            assertTrue(movie.getPosterUrl() == null);
        }
    }

    @Test
    public void getMoviesByGenres() {
        List<Movie> movieList = movieService.getMoviesByGenres(Genres.Action.getGenres(), 5);
        for(Movie movie :movieList){
            assertTrue(movie.getGenres().toLowerCase().contains(Genres.Action.getGenres().toLowerCase()));
        }
    }

    @Test
    public void getMovieListGroupByGenres() {
        HashMap<String, List<Movie>> movieMaps = movieService.getMovieListGroupByGenres();
        for (String genres : movieMaps.keySet()) {
            List<Movie> movieList = movieMaps.get(genres);
            for (Movie movie : movieList) {
                assertTrue(movie.getGenres().toLowerCase().contains(genres.toLowerCase()));
            }
        }
    }


    @Test
    public void getMissingDescriptionMovies() {
        List<Movie> movieList = movieService.getMissingDescriptionMovies(10);
        for (Movie movie : movieList) {
            assertTrue(movie.getPosterUrl() == null);
        }
    }

    @Test
    public void getHighestRatedMovies() {
        List<Movie> movieList = movieService.getHighestRatedMovies(5);
        movieList.forEach(System.out::println);
        assertTrue(!movieList.isEmpty());
    }

    @Test
    public void getMostRatedMovies() {
        List<Movie> movieList = movieService.getMostRatedMovies(5);
        movieList.forEach(System.out::println);
        assertTrue(!movieList.isEmpty());
    }


}
