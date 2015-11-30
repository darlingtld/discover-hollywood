var app = angular.module("MyApp");
app.controller("PickfavController", function ($scope, $http, $location) {
    // if favourite genres list has been selected. then go to movie hall
    // no need to choose them again
    if (sessionStorage['favouriteGenresList'].split(",").length >= 3) {
        $location.path("/moviehall");
        return;
    }
    $http.get("movie/genres/pickfav").success(function (data, status, headers, config) {
        $scope.movieList = [];
        $scope.genres = [];
        for (genres in data) {
            $scope.movieList.push(data[genres]);
            $scope.genres.push(genres);
        }

    });

    $scope.likes = [];

    $scope.likeit = function (genres) {
        if ($scope.likes.length < 3) {
            $scope.likes.push(genres);
            $(event.target).addClass('hidden');
            $(event.target).siblings('.hidden').removeClass('hidden');
        }
    }
    $scope.dislikeit = function (genres) {
        for (var i = 0; i < $scope.likes.length; i++) {
            if ($scope.likes[i] == genres) {
                $scope.likes.splice(i, 1);
            }
        }
        $(event.target).addClass('hidden');
        $(event.target).siblings('.hidden').removeClass('hidden');
    }
    $scope.getLikes = function () {
        if ($scope.likes.length == 0) {
            return;
        } else {
            return "You like " + $scope.likes.reduce(function (p, c) {
                    return p + "," + c;
                });
        }
    };

    $scope.goToMovieHall = function () {
        $http.post('user/favourite_genres/add',
            {
                userId: sessionStorage['userId'],
                favGenresList: $scope.likes
            }).success(function (data) {
            sessionStorage["favouriteGenresList"] = data.favouriteGenresList;
            $location.path("/moviehall");
        })
    }
});