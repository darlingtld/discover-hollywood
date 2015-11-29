package hollywood.controller;

import com.alibaba.fastjson.JSONObject;
import hollywood.pojo.Movie;
import hollywood.pojo.Rating;
import hollywood.service.MovieService;
import hollywood.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Scope("prototype")
@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "search", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<Movie> searchMovie(@RequestBody JSONObject searchStub, HttpServletResponse response) {
        String keyword = searchStub.getString("keyword");
        return movieService.search(keyword, 20);
    }

    @RequestMapping(value = "autocomplete", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<String> autocompleteSearch(@RequestBody JSONObject searchStub, HttpServletResponse response) {
        String keyword = searchStub.getString("keyword");
        List<Movie> movieList = movieService.search(keyword, 10);
        return movieList.stream().map(Movie::getTitle).collect(Collectors.toList());
    }

    @RequestMapping(value = "genres/{genres}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getMoviesByGenres(@PathVariable("genres") String genres, HttpServletResponse response) {
        List<Movie> movieList = movieService.getMoviesByGenres(genres, 10);
        return movieList;
    }

    @RequestMapping(value = "genres/pickfav", method = RequestMethod.GET)
    public
    @ResponseBody
    HashMap<String, List<Movie>> pickFavouriteMovies() {
//        get nine categories
        HashMap<String, List<Movie>> movieList = movieService.getMovieListGroupByGenres();
        HashMap<String, List<Movie>> movieGenresMap = new HashMap<>();
        Random random = new Random(System.currentTimeMillis());
        while (movieGenresMap.size() < 9) {
            String genres = (String) movieList.keySet().toArray()[random.nextInt(movieList.size())];
            movieGenresMap.putIfAbsent(genres, movieList.get(genres));
        }
        return movieGenresMap;
    }

    @RequestMapping(value = "description/{movieId}", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getMovieDescription(@PathVariable("movieId") int movieId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("movieId", movieId);
        jsonObject.put("description", movieService.getMovieDescription(movieId));
        return jsonObject;
    }

    @RequestMapping(value = "rate_highest/{limit}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getHighestRatedMovies(@PathVariable("limit") int limit) {
        return movieService.getHighestRatedMovies(limit);
    }

    @RequestMapping(value = "rate", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void rateMovie(@RequestBody Rating rating) {
        ratingService.save(rating);
    }


}
