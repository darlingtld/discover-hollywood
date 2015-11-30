package hollywood.controller;

import com.alibaba.fastjson.JSONObject;
import hollywood.PropertyHolder;
import hollywood.pojo.Movie;
import hollywood.pojo.User;
import hollywood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * user login
     *
     * @param loginStub
     * @return
     */
    @RequestMapping(value = "signin", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User login(@RequestBody JSONObject loginStub, HttpServletResponse response) {
        String username = loginStub.getString("username");
        String password = loginStub.getString("password");
        try {
            return userService.login(username, password);
        } catch (Exception e) {
            response.setHeader(PropertyHolder.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            return null;
        }
    }

    /**
     * user sign up
     *
     * @param registerStub
     * @param response
     * @return
     */
    @RequestMapping(value = "signup", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User signup(@RequestBody JSONObject registerStub, HttpServletResponse response) {
        String username = registerStub.getString("username");
        String password = registerStub.getString("password");
        try {
            return userService.signup(username, password);
        } catch (Exception e) {
            response.setHeader(PropertyHolder.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            return null;
        }
    }

    /**
     * users pick several genres they like and add them to theri favouriteGenresList
     * @param favMovieStub a json object. {userId:123, favGenresList:"a,b,c"}
     * @return the updated user
     */
    @RequestMapping(value = "favourite_genres/add", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User addFavouriteGenresList(@RequestBody JSONObject favMovieStub) {
        int userId = favMovieStub.getInteger("userId");
        List<String> favGenresList = (List<String>) favMovieStub.get("favGenresList");
        return userService.addFavouriteGenresList(userId, favGenresList);
    }

    /**
     * get user by userId
     * @param userId
     * @return
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public
    @ResponseBody
    User getByUserId(@PathVariable("userId") int userId) {
        return userService.getByUserId(userId);
    }

    /**
     * get user rated movie list
     * @param userId
     * @return
     */
    @RequestMapping(value = "rated_movies/{userId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getRatedMovieList(@PathVariable("userId") int userId) {
        return userService.getRatedMovieList(userId);
    }

    /**
     * get user tagged movie list
     * @param userId
     * @return
     */
    @RequestMapping(value = "tagged_movies/{userId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Movie> getTaggedMovieList(@PathVariable("userId") int userId) {
        return userService.getTaggedMovieList(userId);
    }

}
