var app = angular.module("MyApp", ['ngRoute', 'autocomplete']);
// the service that retrieves some movie title from an url
app.factory('MovieRetriever', function ($http, $q, $timeout) {
    var MovieRetriever = new Object();

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
        userId: sessionStorage["userId"]
    };
    if ($scope.user.username == null) {
        $location.path("/login")
    } else {
        _showLogin();
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
                _showLogin();
                $location.path("/pickfav");
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
        $location.path("/login");
        _hideLogin();
    };

    $scope.search = function () {
        $http.post("movie/search", {
            "keyword": $scope.keyword
        }).success(function (data, status, headers, config) {
            console.log(data)
        });
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
    }).otherwise({
        redirectTo: '/login'
    });
}]);


