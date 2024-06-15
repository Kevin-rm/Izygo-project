// File: G:\S4\MBaovola\izygo\Izygo-project\backend\izygo\src\main\resources\static\js\MyController.js
angular.module('loginApp', [])
    .controller('LoginController', ['$scope', '$http', function($scope, $http) {
        $scope.user = {};
        $scope.login = function() {
            $http.post('http://localhost:8080/api/users/login', $scope.user)
                .then(function(response) {
                    window.location.href = 'landing-page.html';
                })
                .catch(function(error) {
                    $scope.errorMessage = 'Échec de la connexion. Veuillez vérifier vos informations.';
                });
        };
    }]);
