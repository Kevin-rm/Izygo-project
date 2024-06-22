const app = angular.module("izygoApp", ["ngRoute"]);

app.constant("API_BASE_URL", "http://localhost:8080")
    .constant("VIEWS_FOLDER", "views/");

app.filter("uppercase", function () {
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
                if (!currentRoute) return;

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
            };

            scope.$on("$routeChangeSuccess", routeChangeHandler);
            routeChangeHandler();
        }
    };
}])

app.run(function($rootScope) {
    $rootScope.$on("$routeChangeSuccess", function(event, current) {
        $rootScope.title = current.$$route.title;

        if (!$rootScope.title) $rootScope.title = "Izy-go";
    });
});
