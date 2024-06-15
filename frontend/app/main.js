const app = angular.module('myApp', ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl: 'views/login.html',
    })
    .when('/about', {
        templateUrl: 'views/signin.html',
    })
    .otherwise({
        redirectTo: '/'
    });
});
