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
        $("#signin").show();
        $("#loginsuccess").addClass("hidden");
    };

    function _showLogin() {
        $("#signin").hide();
        $("#loginsuccess").removeClass("hidden");
        $("#username").text($scope.user.username);
    }
}).config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        controller: 'LoginController',
        templateUrl: 'includes/signup.html'
    }).when('/pickfav', {
        controller: 'PickfavController',
        templateUrl: 'includes/pickfav.html'
    }).otherwise({
        redirectTo: '/login'
    });
}]);


