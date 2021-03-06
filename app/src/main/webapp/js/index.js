var app = angular.module("MyApp", ['ngRoute', 'autocomplete', 'chart.js', 'angular-jqcloud']);
app.factory('MovieRetriever', function ($http, $q) {
    var MovieRetriever = {};

    MovieRetriever.getmovies = function (keyword) {
        var moviedata = $q.defer();

        $http.post("movie/autocomplete", {keyword: keyword}).success(
            function (response) {
                moviedata.resolve(response);
            });

        return moviedata.promise
    }

    return MovieRetriever;
});

app.controller('IndexController', function ($scope, $location, $http, MovieRetriever) {
    $scope.user = {
        username: sessionStorage["username"],
        userId: sessionStorage["userId"],
        favouriteGenresList: sessionStorage["favouriteGenresList"]
    };
    if ($scope.user.username == null) {
        $location.path("/login")
    } else {
        _showLogin();
        if ($scope.user.favouriteGenresList == null) {
            $location.path("/pickfav");
        } else {
            $location.path("/moviehall");
        }

    }

    $scope.signin = function () {
        console.log($scope.user);
        $http.post("user/signin", {
            "username": $scope.user.username,
            "password": $scope.user.password
        }).success(function (data, status, headers, config) {
            if (data) {
                sessionStorage["username"] = data.username;
                sessionStorage["userId"] = data.userId;
                sessionStorage["favouriteGenresList"] = data.favouriteGenresList;
                _showLogin();
                if (sessionStorage["favouriteGenresList"] == null) {
                    $location.path("/pickfav");
                } else {
                    $location.path("/moviehall");
                }
            } else {
                console.log(headers["message"]);
            }
        }).error(function (data, status, headers, config) {
            alert(headers("message"));
        });
    };

    $scope.logout = function () {
        sessionStorage.removeItem("username");
        sessionStorage.removeItem("userId");
        sessionStorage.removeItem("favouriteGenresList");
        $location.path("/login");
        _hideLogin();
    };

    $scope.search = function () {
        $location.path("/search/" + $scope.keyword);
    };


    $scope.updateMovies = function (typed) {
        $scope.newmovies = MovieRetriever.getmovies(typed);
        $scope.newmovies.then(function (data) {
            $scope.movies = data;
        });
    }

    function _showLogin() {
        $("#signin").hide();
        $("#loginsuccess").removeClass("hidden");
        $("#search").removeClass("hidden");
        $("#username").text($scope.user.username);
    }

    function _hideLogin() {
        $("#signin").show();
        $("#loginsuccess").addClass("hidden");
        $("#search").addClass("hidden");
    }
});

//translate numbers to stars. 2 --> ★★
app.directive('star', function () {
    return {
        restrict: 'AE',
        scope: {},
        link: function (scope, element, attr) {
            var value = attr.starCount;
            var starHtml = '';
            if (value == undefined || value == "") {
                //do nothing
            } else {
                var fullStar = value.split('.')[0];
                for (var i = 0; i < fullStar; i++) {
                    starHtml += '<span class="glyphicon glyphicon-star"></span>';
                }
                if (value.split('.')[1] != undefined) {
                    starHtml += '<span class="glyphicon glyphicon-star-empty"></span>';
                }
            }
            $(element).html(starHtml);
        }
    }
});

//router configuration
app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        controller: 'LoginController',
        templateUrl: 'includes/signup.html'
    }).when('/pickfav', {
        controller: 'PickfavController',
        templateUrl: 'includes/pickfav.html'
    }).when('/moviehall', {
        controller: 'MoviehallController',
        templateUrl: 'includes/moviehall.html'
    }).when('/search/:keyword', {
        controller: 'SearchController',
        templateUrl: 'includes/search.html'
    }).when('/ratedmovies', {
        controller: 'RatedMovieController',
        templateUrl: 'includes/ratedmovie.html'
    }).when('/taggedmovies', {
        controller: 'TaggedMovieController',
        templateUrl: 'includes/taggedmovie.html'
    }).otherwise({
        redirectTo: '/login'
    });
}]);


