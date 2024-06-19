const app = angular.module("izygoApp", ["ngRoute"]);

app.config(function($routeProvider) {
        $routeProvider
            .when("/login", {
                templateUrl: "views/login.html",
                controller: "LoginController"
            })
            .when("/inscription", {
                templateUrl: "views/signup.html",
                controller: "SignupController"
            })
            .when("/landing-page", {
                templateUrl: "views/landing-page.html",
            })
            .when("/profile", {
                templateUrl: "views/profile.html",
            })
            .otherwise({
                redirectTo: "/login"
            });
    })
    .filter("uppercase", function () {
        return function (input) {
            if (input) return input.toUpperCase();
            return input;
        };
    });

app.directive("izygoNavbar", ["$route", function ($route) {
    return {
        restrict: "E",
        template: "<ng-include src=\"navbarTemplate\"></ng-include>",
        link: function(scope) {
            const routeChangeHandler = function() {
                const currentRoute = $route.current && $route.current.originalPath;
                if (currentRoute) {
                    scope.navbarTemplate = "views/navbar-";
                    switch (currentRoute) {
                        case "/login":
                        case "/inscription":
                            scope.navbarTemplate += "alternative";
                            break;
                        default:
                            scope.navbarTemplate += "default";
                            break;
                    }

                    scope.navbarTemplate += ".html";
                }
            };

            scope.$on("$routeChangeSuccess", routeChangeHandler);
            routeChangeHandler();
        }
    };
}])
