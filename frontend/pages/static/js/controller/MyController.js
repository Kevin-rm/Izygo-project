var app = angular.module('myApp', []);

app.controller('SignupController', ['$scope', '$http', function($scope, $http) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post('http://localhost:8080/api/users/register', $scope.user)
            .then(function(response) {
                // Convertir l'objet en JSON
                console.log(JSON.stringify(response.data)); // Affiche les données de la réponse en JSON
                alert('Inscription réussie !');
                window.location.href = 'login.html';
            })
            .catch(function(error) {
                // Convertir l'objet d'erreur en JSON
                console.error(JSON.stringify(error.data)); // Affiche les données de l'erreur en JSON
                alert('Erreur lors de l\'inscription: ' + error);
            });
    };
}]);

app.controller('LoginController', ['$scope', '$http', function($scope, $http) {
    $scope.user = {};

    $scope.login = function() {
        $http.post('http://localhost:8080/api/users/login', $scope.user)
            .then(function(response) {
                // Convertir l'objet en JSON
                console.log(response);
                alert(JSON.stringify(response.data)); // Affiche les données de la réponse en JSON
                //window.location.href = 'landing-page.html'; 
            })
            .catch(function(error) {
                // Convertir l'objet d'erreur en JSON
                console.error(error.data); // Affiche les données de l'erreur en JSON
                $scope.errorMessage = 'Échec de la connexion. Veuillez vérifier vos informations: ' + error.data.message;
            });
    };
}]);

