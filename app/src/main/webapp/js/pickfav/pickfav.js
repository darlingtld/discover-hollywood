var app = angular.module("MyApp");
app.controller("PickfavController", function ($scope, $http, $location) {
    $http.get("movie/genres/pickfav").success(function (data, status, headers, config) {
        $scope.movieList = [];
        for (genres in data) {
            $scope.movieList.push(data[genres]);
        }

    });
});