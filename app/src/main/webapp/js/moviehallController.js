/**
 * Created by lingda on 2015/11/28.
 */
var app = angular.module("MyApp");
app.controller("MoviehallController", function ($scope, $http, $location, userService) {
    if (sessionStorage["userId"] == null) {
        $location.path("/login")
        return;
    } else if (sessionStorage["favouriteGenresList"] == null || sessionStorage["favouriteGenresList"].split(",").length < 3) {
        $location.path("/pickfav")
        return;
    }
    //if user's recommended movie list has not been calculated by lenskit, then just get his/her genres style ones
    userService.getUser().then(function (user) {
        if (user.recommendMovieList == null || user.recommendMovieList.length == 0) {
            $http.post("recommend/genres/6", {
                favouriteGenresList: sessionStorage["favouriteGenresList"]
            }).success(function (data) {
                $scope.movieRecommendMovieList = data;
            });
        } else {
            $scope.movieRecommendMovieList = user.recommendMovieList.slice(0, 6);
        }

    });


    //top 6 highest rated movies
    $http.get("movie/rate_highest/6").success(function (data) {
        $scope.movieRateHighest = data;
    });

    //top 6 most rated movies
    $http.get("movie/rate_most/6").success(function (data) {
        $scope.movieRateMost = data;
    });
    //top 6 recently release movies
    $http.get("movie/recent/6").success(function (data) {
        $scope.movieNewbies = data;
    });

    //top 6 most tagged movies
    $http.get("movie/tag_most/6").success(function (data) {
        $scope.movieTagMost = data;
    });

    $scope.rateMap = [
        {name: "0.5 ☆", value: "0.5"},
        {name: "1.0 ★", value: "1.0"},
        {name: "1.5 ★☆", value: "1.5"},
        {name: "2.0 ★★", value: "2.0"},
        {name: "2.5 ★★☆", value: "2.5"},
        {name: "3.0 ★★★", value: "3.0"},
        {name: "3.5 ★★★☆", value: "3.5"},
        {name: "4.0 ★★★★", value: "4.0"},
        {name: "4.5 ★★★★☆", value: "4.5"},
        {name: "5.0 ★★★★★", value: "5.0"},

    ]

    $scope.setMovie = function (movieId) {
        $scope.movieIdToRate = movieId;
    }

    $scope.rate = function () {
        $http.post("movie/rate", {
            userId: sessionStorage['userId'],
            movieId: $scope.movieIdToRate,
            rating: $scope.rating.value,
            timestamp: new Date()
        }).success(function (data) {
            alert("Thanks for your rating");
            $scope.rating = {};
            $('#rateModal .close').click();
        })
    }

    $scope.showMovieTrend = function (movieId) {
        $http.get('movie/rate_trend/' + movieId).success(function (data) {
            var labels = [];
            var datas = [];
            for (var i = 0; i < data.length; i++) {
                labels.push(data[i].year);
                datas.push(data[i].rating.toFixed(3));
            }
            $scope.labels = labels;
            $scope.series = ['Ratings'];
            $scope.data = [datas];
        });
    }


    $scope.showPopover = function (movieId) {
        $http.get('movie/tags/' + movieId).success(function (data) {
            $scope.words = data;
        })
        $scope.popoverIsVisible = true;
        $('div.pop').css('top', (event.pageY - 100) + 'px');
        $('div.pop').css('left', event.pageX + 'px');
    };

    $scope.hidePopover = function () {
        $scope.popoverIsVisible = false;
    };

});