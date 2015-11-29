var app = angular.module("MyApp");
app.controller("LoginController", function ($scope, $rootScope, $http, $location) {
    $scope.signup = function () {
        $http.post("user/signup", {
            "username": $scope.username,
            "password": $scope.password
        }).success(function (data, status, headers, config) {
            if (data) {
                sessionStorage["username"] = data.username;
                sessionStorage["userId"] = data.userId;

                $("#signupModal .close").click();
                $('body').removeClass('modal-open');
                $('.modal-backdrop').remove();
                $("#signin").hide();
                $("#loginsuccess").removeClass("hidden");
                $("#search").removeClass("hidden");
                $("#username").text(data.username);
                $location.path("/pickfav");
            } else {
                console.log(headers["message"]);
            }
        }).error(function (data, status, headers, config) {
            alert(headers("message"));
        });
    };


});