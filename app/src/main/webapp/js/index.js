angular.module("MyApp", ['ngRoute']).
controller('IndexController', function ($scope, $location, $http) {
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
}).config(['$routeProvider', function ($routeProvider) {
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


