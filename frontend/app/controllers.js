app.controller("MainController", ["UserFactory", function (UserFactory) {

}]).controller("LoginController", ["$scope", "$http", "$window", "UserFactory", "API_BASE_URL", function($scope, $http, $window, UserFactory, API_BASE_URL) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post(API_BASE_URL + "/login", $scope.user)
            .then(function(response) {
                UserFactory.setUser(response.data);
                $window.location.href = "#!/";
            })
            .catch(function(error) {
                console.error(error);
            });
    };
}]).controller("SignupController", ["$scope", "$http", "$window", "UserFactory", "API_BASE_URL", function($scope, $http, $window, UserFactory, API_BASE_URL) {
    $scope.user = {
        roleId: 1 // Client
    };
    // $scope.success = null;
    $scope.errors = {}

    $scope.submitForm = function() {
        $http.post(API_BASE_URL + "/api/user/register", $scope.user)
            .then(function(response) {
                $scope.errors = {};
                UserFactory.setUser(response.data);
                $window.location.href = "#!/";
            })
            .catch(function(error) {
                if (error.status === 400 && error.data)
                    $scope.errors = error.data;
            });
    };
}]).controller('ProfileController', ['$scope', '$http', '$window', function($scope, $http, $window) {
    $scope.activeReservations = [];
    $scope.pastReservations = [];

    const userId = $window.sessionStorage.getItem('user_id');

    if (userId) {
        $http.get('http://localhost:8080/api/reservationsbyuser/user/' + userId)
            .then(function(response) {
                const reservations = response.data;
                const now = new Date();
                reservations.forEach(reservation => {
                    const reservationDate = new Date(reservation.date);
                    if (reservationDate >= now) {
                        $scope.activeReservations.push(reservation);
                    } else {
                        $scope.pastReservations.push(reservation);
                    }
                });
            })
            .catch(function(error) {
                alert('Erreur lors de la récupération des réservations : ' + error.data.message);
            });
    } else {
        alert('Utilisateur non connecté.');
        window.location.href = '#!/login';
    }
}]).controller("RouteSearchController", ["$scope", "BusStopFactory", function ($scope, BusStopFactory) {
    $scope.stops = BusStopFactory.getAll()
        .then(function (data) {
            $scope.stops = data;
        })
        .catch(function (error) {
            console.error(error);
        });
    $scope.selectedDepartureStop = null;
    $scope.selectedArrivalStop   = null;
}]);
