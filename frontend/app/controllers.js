app.controller('SignupController', ['$scope', '$http',function($scope, $http) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post('http://localhost:8080/api/users/register', $scope.user)
            .then(function(response) {
                // console.log(JSON.stringify(response.data)); // Affiche les données de la réponse en JSON
                // alert('Inscription réussie !');
                window.location.href = '#!/';
            })
            .catch(function(error) {
                // Convertir l'objet d'erreur en JSON
                //console.error(JSON.stringify(error.data)); // Affiche les données de l'erreur en JSON
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
                console.log(user.id);
                $window.sessionStorage.setItem('user_Id', user.id);
                window.location.href = '#!/landing-page';
            })
            .catch(function(error) {
                // Convertir l'objet d'erreur en JSON
                //console.error(error.data); // Affiche les données de l'erreur en JSON
                alert('Échec de la connexion. Veuillez vérifier vos informations: ' + error.data.message);
                //$scope.errorMessage = 'Échec de la connexion. Veuillez vérifier vos informations: ' + error.data.message;
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
