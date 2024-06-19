const app = angular.module("izygoApp", ["ngRoute"]);

app.config(function($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "views/login.html",
            })
            .when("/signin", {
                templateUrl: "views/signin.html",
            })
            .when("/landing-page", {
                templateUrl: "views/landing-page.html",
            })
            .when("/profile", {
                templateUrl: "views/profile.html",
            })
            .otherwise({
                redirectTo: "/"
            });
    })
    .filter("uppercase", function () {
        return function (input) {
            if (input) return input.toUpperCase();
            return input;
        };
    });
