/**
 * Created by lingda on 2015/11/28.
 */
var app = angular.module("MyApp");
app.controller("TaggedMovieController", function ($scope, $http, $location, userService) {
    if (sessionStorage["userId"] == null) {
        $location.path("/login")
        return;
    }
    $http.get('user/tagged_movies/' + sessionStorage["userId"]).success(function (data) {
        $scope.taggedMovieList = [];
        for (var i = 0; i < data.length; i = i + 6) {
            $scope.taggedMovieList.push(data.slice(i, i + 6));
        }
    })
});