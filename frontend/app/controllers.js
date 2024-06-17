app.controller('SignupController', ['$scope', '$http','$location',function($scope, $http) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post('http://localhost:8080/api/users/register', $scope.user)
            .then(function(response) {
                console.log(JSON.stringify(response.data)); // Affiche les données de la réponse en JSON
                // alert('Inscription réussie !');
                $location.path('/');
            })
            .catch(function(error) {
                // Convertir l'objet d'erreur en JSON
                //console.error(JSON.stringify(error.data)); // Affiche les données de l'erreur en JSON
                alert('Erreur lors de l\'inscription: ' + error.data.message);
            });
    };
}]);

app.controller('LoginController', ['$scope', '$http', function($scope, $http) {
    $scope.user = {};

    $scope.login = function() {
        $http.post('http://localhost:8080/api/users/login', $scope.user)
            .then(function(response) {
                const user = JSON.stringify(response.data);
                // alert('Connexion reussie !'); // Affiche les données de la réponse en JSON
                
            })
            .catch(function(error) {
                // Convertir l'objet d'erreur en JSON
                //console.error(error.data); // Affiche les données de l'erreur en JSON
                alert('Échec de la connexion. Veuillez vérifier vos informations: ' + error.data.message);
                //$scope.errorMessage = 'Échec de la connexion. Veuillez vérifier vos informations: ' + error.data.message;
            });
    };
}]);
