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
}]).controller("RouteSearchController", ["$scope", "BusStopFactory", "SharedService", function ($scope, BusStopFactory, SharedService) {
    $scope.showResults = false;

    $scope.stops = [];
    BusStopFactory.getAll()
        .then(function (data) {
            $scope.stops = data;
        })
        .catch(function (error) {
            console.error(error);
        });
    $scope.departureStop = null;
    $scope.arrivalStop   = null;

    $scope.excludeSelectedArrival = function(stop) {
        return SharedService.excludeSelectedArrival(stop, $scope.arrivalStop);
    };

    $scope.excludeSelectedDeparture = function(stop) {
        return SharedService.excludeSelectedDeparture(stop, $scope.departureStop);
    };

    $scope.time1 = new Date("2000-01-01T07:00:00");
    $scope.time2 = new Date("2000-01-01T08:00:00");
    $scope.numberOfSeats = 1;

    $scope.submitForm = function () {
        $scope.showResults = true;
    };
}]);

app.controller('resultsController', ['$scope', "$timeout", function ($scope, $timeout) {
    $scope.depart = "Analakely";
    $scope.arrivee = "Andoharanofotsy";
    $scope.currentIndex = 0; // Initialize current index

    $scope.propositions = [
        {
            buses: 3,
            distance: '100 m',
            time: '1h 00',
            bus1: { number: 1, depart: 'Analakely', seats: 2, time: '08:00' },
            bus2: { number: 2, depart: 'Anosy', seats: 2, time: '09:00' },
            showContent: false
        },
        {
            buses: 2,
            distance: '200 m',
            time: '1h 30',
            bus1: { number: 1, depart: 'Anosy', seats: 1, time: '10:00' },
            bus2: { number: 2, depart: 'Tanjombato', seats: 1, time: '11:00' },
            showContent: false
        }
    ];

    // Function to toggle showContent and close others
    $scope.toggle = function (proposition) {
        proposition.showContent = !proposition.showContent;
        $scope.propositions.forEach(function (prop, index) {
            if (prop !== proposition) {
                prop.showContent = false; // Close others
            }
        });
    };

    // Function to show previous proposition
    $scope.showPrevious = function () {
        if ($scope.currentIndex > 0) {
            $scope.propositions[$scope.currentIndex].showContent = false; // Close current
            $scope.currentIndex--;
        }
    };

    // Function to show next proposition
    $scope.showNext = function () {
        if ($scope.currentIndex < $scope.propositions.length - 1) {
            $scope.propositions[$scope.currentIndex].showContent = false; // Close current
            $scope.currentIndex++;
        }
    };

    function initMap() {
        var mainMap = L.map('map').setView([-18.9064, 47.5246], 13);

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
        }).addTo(mainMap);

        var startPoint = [-18.9064, 47.5246];
        var endPoint = [-18.97978, 47.5328];

        var mainPolyline = L.polyline([startPoint, endPoint], {color: 'blue'}).addTo(mainMap);

        L.marker(startPoint).addTo(mainMap).bindPopup('Analakely');
        L.marker(endPoint).addTo(mainMap).bindPopup('Andoharanofotsy');
    }

    // Use $timeout to ensure DOM is ready before initializing the map
    $timeout(function() {
        initMap();
    }, 0);
}]);
