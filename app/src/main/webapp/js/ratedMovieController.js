/**
 * Created by lingda on 2015/11/28.
 */
var app = angular.module("MyApp");
app.controller("RatedMovieController", function ($scope, $http, $location, userService) {
    if (sessionStorage["userId"] == null) {
        $location.path("/login")
        return;
    }
    $http.get('user/rated_movies/' + sessionStorage["userId"]).success(function (data) {
        $scope.ratedMovieList = [];
        for (var i = 0; i < data.length; i = i + 6) {
            $scope.ratedMovieList.push(data.slice(i, i + 6));
        }
    })
});