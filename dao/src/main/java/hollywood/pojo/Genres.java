package hollywood.pojo;

/**
 * Created by lingda on 2015/11/27.
 */
public enum Genres {
    Action("Action"),
    Adventure("Adventure"),
    Animation("Animation"),
    Children("Children\'s"),
    Comedy("Comedy"),
    Crime("Crime"),
    Documentary("Documentary"),
    Drama("Drama"),
    Fantasy("Fantasy"),
    FilmNoir("Film-Noir"),
    Horror("Horror"),
    Musical("Musical"),
    Mystery("Mystery"),
    Romance("Romance"),
    SciFi("SciFi"),
    Thriller("Thriller"),
    War("War"),
    Western("Western");

    private String genres;

    Genres(String genres) {
        this.genres = genres;
    }

    public String getGenres() {
        return this.genres;
    }
}
