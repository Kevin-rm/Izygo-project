angular.module('signupApp', [])
.controller('SignupController', ['$scope', '$http', function($scope, $http) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post('http://localhost:8080/api/users/register', $scope.user)
        .then(function(response) {
            alert('Inscription réussie !');
            window.location.href = 'login.html';
        }, function(error) {
            alert('Erreur lors de l\'inscription ');
        });
    };
}]);
