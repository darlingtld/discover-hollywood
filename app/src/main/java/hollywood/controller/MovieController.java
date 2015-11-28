package hollywood.controller;

import com.alibaba.fastjson.JSONObject;
import hollywood.pojo.Movie;
import hollywood.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "search", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<Movie> searchMovie(@RequestBody JSONObject searchStub, HttpServletResponse response) {
        String keyword = searchStub.getString("keyword");
        return movieService.search(keyword);
    }

    @RequestMapping(value = "autocomplete", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<String> autocompleteSearch(@RequestBody JSONObject searchStub, HttpServletResponse response) {
        String keyword = searchStub.getString("keyword");
        List<Movie> movieList = movieService.search(keyword);
        return movieList.stream().map(Movie::getTitle).collect(Collectors.toList());
    }

    @RequestMapping(value = "genres/{genres}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getMoviesByGenres(@PathVariable("genres") String genres, HttpServletResponse response) {
        List<Movie> movieList = movieService.getMoviesByGenres(genres);
        return movieList;
    }

    @RequestMapping(value = "genres/pickfav", method = RequestMethod.GET)
    public
    @ResponseBody
    HashMap<String, List<Movie>> pickFavouriteMovies() {
        HashMap<String, List<Movie>> movieList = movieService.getMovieListGroupByGenres();
        return movieList;
    }

}
