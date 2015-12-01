package hollywood.controller;

import com.alibaba.fastjson.JSONObject;
import hollywood.pojo.Movie;
import hollywood.pojo.Rating;
import hollywood.pojo.Tag;
import hollywood.service.MovieService;
import hollywood.service.RatingService;
import hollywood.service.StatTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private StatTagsService statTagsService;

    /**
     * users can search movies by titles and tags
     *
     * @param searchStub {keyword:keyword}
     * @return movieList
     */
    @RequestMapping(value = "search", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<Movie> searchMovie(@RequestBody JSONObject searchStub) {
        String keyword = searchStub.getString("keyword");
        List<Movie> movieList = movieService.searchMoviesByTitle(keyword, 20);
        movieList.addAll(movieService.searchMoviesByTag(keyword, 20));
        return movieList;
    }

    /**
     * autocomplete. work with the frontend searchMoviesByTitle input box.
     *
     * @param searchStub {keyword:keyword}
     * @return a list of possible matches
     */
    @RequestMapping(value = "autocomplete", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<String> autocompleteSearch(@RequestBody JSONObject searchStub) {
        String keyword = searchStub.getString("keyword");
//        give 10 matches
        List<Movie> movieList = movieService.searchMoviesByTitle(keyword, 10);
//        return the names of the movies. " may cause some regex problems on the frontend.
        return movieList.stream().map(movie -> movie.getTitle().replace("\"", "")).collect(Collectors.toList());
    }

    /**
     * get movie list by genres
     *
     * @param genres
     * @return movie list
     */
    @RequestMapping(value = "genres/{genres}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getMoviesByGenres(@PathVariable("genres") String genres) {
        List<Movie> movieList = movieService.getMoviesByGenres(genres, 10);
        return movieList;
    }

    /**
     * return nine genres comprised of list of movies for user to pick his/her style
     *
     * @return a hashmap whose key is the name of the genres and the value is a list of movies of the genres
     */
    @RequestMapping(value = "genres/pickfav", method = RequestMethod.GET)
    public
    @ResponseBody
    HashMap<String, List<Movie>> pickFavouriteMovies() {
//        get nine genres movie list
        int genresCount = 9;
        HashMap<String, List<Movie>> movieList = movieService.getMovieListGroupByGenres();
        HashMap<String, List<Movie>> movieGenresMap = new HashMap<>();
        Random random = new Random(System.currentTimeMillis());
        while (movieGenresMap.size() < genresCount) {
            String genres = (String) movieList.keySet().toArray()[random.nextInt(movieList.size())];
            movieGenresMap.putIfAbsent(genres, movieList.get(genres));
        }
        return movieGenresMap;
    }

    /**
     * get the movie description of the given movieId
     *
     * @param movieId
     * @return a json object. something like this {movieId:11,description:hello}
     */
    @RequestMapping(value = "description/{movieId}", method = RequestMethod.GET)
    public
    @ResponseBody
    JSONObject getMovieDescription(@PathVariable("movieId") int movieId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("movieId", movieId);
        jsonObject.put("description", movieService.getMovieDescription(movieId));
        return jsonObject;
    }

    /**
     * get highest rated movie list
     *
     * @param limit
     * @return a list of movies
     */
    @RequestMapping(value = "rate_highest/{limit}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getHighestRatedMovies(@PathVariable("limit") int limit) {
        return movieService.getHighestRatedMovies(limit);
    }

    /**
     * get most rated movies
     *
     * @param limit
     * @return a list of movies
     */
    @RequestMapping(value = "rate_most/{limit}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getMostRatedMovies(@PathVariable("limit") int limit) {
        return movieService.getMostRatedMovies(limit);
    }

    /**
     * get recently released movies
     *
     * @param limit
     * @return
     */
    @RequestMapping(value = "recent/{limit}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getRecentlyReleasedMovies(@PathVariable("limit") int limit) {
        return movieService.getRecentlyReleasedMovies(limit);
    }

    /**
     * user gives a rating for some movie
     *
     * @param rating pojo Rating.java
     */
    @RequestMapping(value = "rate", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void rateMovie(@RequestBody Rating rating) {
        ratingService.save(rating);
    }

    /**
     * user gives a tag for some movie
     *
     * @param tag pojo Tag.java
     */
    @RequestMapping(value = "tag", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    void rateMovie(@RequestBody Tag tag) {
        statTagsService.save(tag);
    }


}
