/**
 * Created by lingda on 2015/11/28.
 */
var app = angular.module("MyApp");
app.controller("MoviehallController", function ($scope, $http, $location) {
    $http.get("movie/rate_highest/6").success(function (data) {
        $scope.movieRateHighest = data;
    })
});