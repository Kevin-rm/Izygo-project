app.controller("LoginController", ["$scope", "$http", "$window", function($scope, $http, $window) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post("http://localhost:8080/api/user/login", $scope.user)
            .then(function(response) {
                const user = response.data;
                console.log(user);
                window.location.href = "#!/landing-page";
            })
            .catch(function(error) {

            });
    };
}]);

app.controller("SignupController", ["$scope", "$http", function($scope, $http) {
    $scope.user = {
        roleId: 1
    };

    $scope.submitForm = function() {
        $http.post("http://localhost:8080/api/user/register", $scope.user)
            .then(function(response) {
                console.log(response)
            })
            .catch(function(error) {

            });
    };
}]);

app.controller('ProfileController', ['$scope', '$http', '$window', function($scope, $http, $window) {
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
}]);
