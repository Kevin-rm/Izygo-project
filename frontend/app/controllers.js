app.controller("MainController", ["UserFactory", function (UserFactory) {

}]).controller("LoginController", ["$scope", "$http", "$window", "UserFactory", "API_URL", function($scope, $http, $window, UserFactory, API_URL) {
    $scope.user = {};
    
    $scope.submitForm = function() {
        $http.post(API_URL + "/login", $scope.user)
            .then(function(response) {
                UserFactory.setUser(response.data);
                const userfact=UserFactory.getUser()
                console.log(userfact);
                $window.location.href = "#!/";
            })
            .catch(function(error) {
                    $scope.errors = error.data.message;
            });
    };
}]).controller("SignupController", ["$scope", "$http", "$window", "UserFactory", "API_URL", function($scope, $http, $window, UserFactory, API_URL) {
    $scope.user = {
        roleId: 1 
    };
    $scope.errors = {}
    $scope.name='';

    $scope.submitForm = function() {
        $http.post(API_URL + "/api/user/register", $scope.user)
            .then(function(response) {
                $scope.errors = {};
                $window.location.href = "#!/login";
            })
            .catch(function(error) {
                if (error.status === 400 && error.data)
                    $scope.errors = error.data;
            });
    };
}]).controller("ProfileController", ["$scope", "$http", "$window", "UserFactory","$routeParams",  "API_URL", function($scope, $http, $window, UserFactory) {

    const user = UserFactory.getUser();
    $scope.name='';

    if (!user) {
        $window.location.href = '#!/login';
    } else {
        console.log(user);
        $scope.activeReservations = [];
        $scope.pastReservations = [];
        
        if (user) {
            $http.get('http://localhost:8080/api/profileuser/user/' + user.id)
                .then(function(response) {
                    $scope.name=user;
                    const reservations = response.data;
                    UserFactory.setReservations(reservations);

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
        $scope.showSeats = function(reservation) {
            console.log('Clicked reservation:', reservation);
            $window.location.href = '#!/reservation-active/' + 1 + '/' + reservation.reservationId;
        };
    }
    
}]).controller("ListSeatController", ["$scope", "$http", "$routeParams", function($scope, $http, $routeParams) {
    const userId = $routeParams.userId;
    const reservationId = $routeParams.reservationId;

    $scope.selectedReservationSeats = [];

    if (userId && reservationId) {
        $http.get('http://localhost:8080/api/profileuser/user/' + userId + '/reservation/' + reservationId + '/seats')
            .then(function(response) {
                $scope.selectedReservationSeats = response.data;
            })
            .catch(function(error) {
                console.error('Erreur lors du chargement des sièges réservés :', error);
            });
    } else {
        console.error('userId or reservationId is undefined');
    }
}]);

