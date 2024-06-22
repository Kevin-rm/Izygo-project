app.controller('SignupController', ['$scope', '$http',function($scope, $http) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post('http://localhost:8080/api/users/register', $scope.user)
            .then(function(response) {
                alert('Inscription réussie !');
                window.location.href = '#!/';
            })
            .catch(function(error) {
                alert('Erreur lors de l\'inscription: ' + error.data.message);
            });
    };
}]);  

app.controller('LoginController', ['$scope', '$http','$window', function($scope, $http, $window) {
    $scope.user = {};

    $scope.login = function() {
        $http.post('http://localhost:8080/api/users/login', $scope.user)
            .then(function(response) {
                const user = response.data;
                $window.sessionStorage.setItem('user_Id', user.id);
                console.log($window.sessionStorage.getItem('user_Id'));
                window.location.href = '#!/landing-page';
            }) 
            .catch(function(error) {
                alert('Échec de la connexion. Veuillez vérifier vos informations: ' + error.data.message);
            });
    };
}]);

app.controller('ProfileController', ['$scope', '$http', '$window', function($scope, $http, $window) {
    $scope.activeReservations = [];
    $scope.pastReservations = [];

    // const userId = $window.sessionStorage.getItem('user_Id');
    // console.log(userId);
    const userId=1;
    
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
