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
}]);
