package hollywood.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Created by lingda on 2015/11/27.
 */
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private int userId;
    private String username;
    private String password;
    private List<Movie> ratedMovieList;
    private List<String> favouriteGenresList;
    private List<Movie> recommendMovieList;

    public List<Movie> getRecommendMovieList() {
        return recommendMovieList;
    }

    public void setRecommendMovieList(List<Movie> recommendMovieList) {
        this.recommendMovieList = recommendMovieList;
    }

    private List<Integer> friendIdList;
    private Date lastLoginTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Movie> getRatedMovieList() {
        return ratedMovieList;
    }

    public void setRatedMovieList(List<Movie> ratedMovieList) {
        this.ratedMovieList = ratedMovieList;
    }

    public List<String> getFavouriteGenresList() {
        return favouriteGenresList;
    }

    public void setFavouriteGenresList(List<String> favouriteGenresList) {
        this.favouriteGenresList = favouriteGenresList;
    }

    public List<Integer> getFriendIdList() {
        return friendIdList;
    }

    public void setFriendIdList(List<Integer> friendIdList) {
        this.friendIdList = friendIdList;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
