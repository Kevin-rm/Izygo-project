const app = angular.module("izygoApp", ["ngRoute"]);

app.constant("API_BASE_URL", "http://localhost:8080")
    .constant("VIEWS_FOLDER", "views/");

app.filter("uppercase", function () {
    return function (input) {
        if (input) return input.toUpperCase();
        return input;
    };
}).filter("timeFormat", function() {
    return function(date) {
        if (!date) return;
        const hours = date.getHours();
        const minutes = date.getMinutes();
        return (hours < 10 ? "0" : "") + hours + "h " + (minutes < 10 ? "0" : "") + minutes;
    };
});

app.run(function($rootScope) {
    $rootScope.$on("$routeChangeSuccess", function(event, current) {
        $rootScope.title = current.$$route.title;

        if (!$rootScope.title) $rootScope.title = "Izy-go";
    });
});
