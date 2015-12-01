/**
 * Created by lingda on 2015/11/28.
 */
var app = angular.module("MyApp");
app.controller("SearchController", function ($scope, $http, $routeParams) {
    $http.post("movie/search", {keyword: $routeParams.keyword}).success(function (data, status, headers, config) {
        $scope.movieList = data;
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

    $scope.tag = function () {
        $http.post("movie/tag", {
            userId: sessionStorage['userId'],
            movieId: $scope.movieIdToRate,
            tag: $scope.movieTag,
            timestamp: new Date()
        }).success(function (data) {
            alert("Thanks for your tagging");
            $scope.movieTag = {};
            $('#tagModal .close').click();
        })
    }
});

app.filter('limitSize', function () {
    return function (text) {
        var limit = 200;
        if (text == null) {
            return;
        }
        if (text.length <= limit) {
            return text;
        } else {
            return text.substr(0, limit) + "...";
        }
    };
});