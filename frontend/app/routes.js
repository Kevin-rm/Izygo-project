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
        .when("/inscription", {
            templateUrl: VIEWS_FOLDER + "signup.html",
            controller: "SignupController",
            title: "Inscription"
        })
        .when("/recherche-itineraire", {
            templateUrl: VIEWS_FOLDER + "route-search.html",
            controller: "RouteSearchController",
            title: "Recherche d'itinéraire"
        })
        .when("/reservation", {
            templateUrl: VIEWS_FOLDER + "bus-booking.html",
            title: "Réservation"
        })
        .when("/notifications", {
            templateUrl: VIEWS_FOLDER + "notification.html",
            controller: "NotificationController",
            title: "Vos notifications"
        })
        .when("/profil", {
            templateUrl: VIEWS_FOLDER + "profile.html",
            title: "Votre profil"
        });
}]);