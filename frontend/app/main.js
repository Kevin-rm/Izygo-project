const app = angular.module("izygoApp", ["ngRoute"]);

app.constant("API_URL", "http://localhost:8080")
    .constant("VIEWS_FOLDER", "views/");

app.config(["$routeProvider", "VIEWS_FOLDER", function($routeProvider, VIEWS_FOLDER) {
    $routeProvider
        .when("/", {
            templateUrl: VIEWS_FOLDER + "landing-page.html",
            title: "Accueil"
        })
        .when("/login", {
            templateUrl: VIEWS_FOLDER + "login.html",
            controller: "LoginController",
            title: "Connexion"
        })
        .when("/profil", {
            templateUrl: VIEWS_FOLDER + "profile.html",
            controller: "ProfileController",
            title: "profil"
        })
        .when("/reservation-active/:userId/:reservationId", {
            templateUrl: VIEWS_FOLDER + "reservation-active.html",
            controller: "ListSeatController",
            title: "profil"
        })
        .when("/landing-page", {
            templateUrl: "views/landing-page.html",
        })
        .when("/inscription", {
            templateUrl: VIEWS_FOLDER + "signup.html",
            controller: "SignupController",
            title: "Inscription"
        })
        .when("/recherche-itineraire", {
            templateUrl: VIEWS_FOLDER + "route-search.html",
            title: "Recherche"
        })
        .when("/reservation", {
            templateUrl: VIEWS_FOLDER + "bus-booking.html",
            controller:"ReservationController",
            title: "Réservation"
        })
        .when("/choosingSeat", {
            templateUrl: VIEWS_FOLDER + "choosing-seat.html",
            controller:"ChoosingSeatController",
            title: "Choix de place"
        })
        .when("/notification", {
            templateUrl: VIEWS_FOLDER + "notification.html",
            title: "Vos notifications"
        })
        .when("/annulation",{
            templateUrl: VIEWS_FOLDER + "annulation.html",
            title: "Annulation"
        })
        .when("/get-ticket",{
            templateUrl: VIEWS_FOLDER + "get-ticket.html",
            controller: "ConfirmationController",
            title: "Merci d'avoir choisi IzyGo"
        })
}]);

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
