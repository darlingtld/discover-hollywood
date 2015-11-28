/**
 * Created by lingda on 2015/11/28.
 */
var app = angular.module("MyApp");
app.controller("SearchController", function ($scope, $http, $routeParams) {
    $http.post("movie/search", {keyword: $routeParams.keyword}).success(function (data, status, headers, config) {
        $scope.movieList = data;
    });
});