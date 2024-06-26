app.config(["$routeProvider", "VIEWS_FOLDER", function($routeProvider, VIEWS_FOLDER) {
    $routeProvider
        .when("/", {
            templateUrl: VIEWS_FOLDER + "landing-page.html",
            controller: "LandingPageController",
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
            controller: "ReservationController",
            title: "Réservation"
        })
        .when("/notifications", {
            templateUrl: VIEWS_FOLDER + "notification.html",
            controller: "NotificationController",
            title: "Vos notifications"
        })
        .when("/profil", {
            templateUrl: VIEWS_FOLDER + "profile.html",
            controller: "ProfileController",
            title: "Votre profil"
        })
        .when("/depot-argent", {
            templateUrl: VIEWS_FOLDER + "deposit.html",
            controller: "DepositController",
            title: "Dépôt d'argent"
        })
        .when("/reservation-active",{
            templateUrl:VIEWS_FOLDER + "reservation-active.html",
            controller:"ProfileSeatsController",
            title : "Mes sièges"
        }).when("/annulation",{
            templateUrl: VIEWS_FOLDER +"annulation.html",
            controller : "annulationController",
            title : "Annulation"
        })
        .when("/dashboard", {
            templateUrl: VIEWS_FOLDER + "dashboard.html",
            controller: "DashboardController",
            title: "Dashboard"
        });
}]);
