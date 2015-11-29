/**
 * Created by lingda on 2015/11/29.
 */
var app = angular.module("MyApp");

app.service("userService", function ($q, $http) {
    this.getUser = function () {
        var deferred = $q.defer();
        $http.get("user/" + sessionStorage["userId"]).success(
            function (response) {
                deferred.resolve(response);
            });
        return deferred.promise;
    };
});