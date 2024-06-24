app.controller("LandingPageController", ["$scope", "SharedService", "UserFactory", function ($scope, SharedService, UserFactory) {
    SharedService.authenticate();

    const user = UserFactory.getUser();
    $scope.firstname = user.firstname;
    $scope.lastname = user.lastname;
}]).controller("LoginController", ["$scope", "$http", "$location", "UserFactory", "API_BASE_URL", function($scope, $http, $location, UserFactory, API_BASE_URL) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post(API_BASE_URL + "/login", $scope.user)
            .then(function(response) {
                $scope.error = null;

                UserFactory.setUser(response.data);
                $location.path("/");
            })
            .catch(function(error) {
                if (error.status === 401 && error.data)
                    $scope.error = error.data.message;
            });
    };
}]).controller("LogoutController", ["$scope", "$location", "UserFactory", function ($scope, $location, UserFactory) {
    $scope.logout = function () {
        UserFactory.clearUser();

        $location.path("/login");
    };
}]).controller("SignupController", ["$scope", "$http", "$location", "UserFactory", "API_BASE_URL", function($scope, $http, $location, UserFactory, API_BASE_URL) {
    $scope.user = {
        roleId: 1 // role user
    };
    $scope.errors = {}

    $scope.submitForm = function() {
        $http.post(API_BASE_URL + "/user/register", $scope.user)
            .then(function(response) {
                $scope.errors = {};
                UserFactory.setUser(response.data);
                $location.path("/");
            })
            .catch(function(error) {
                if (error.status === 400 && error.data)
                    $scope.errors = error.data;
            });
    };
}]).controller("RouteSearchController", ["$scope", "$http", "$timeout", "BusStopFactory", "SharedService", "API_BASE_URL", function ($scope, $http, $timeout, BusStopFactory, SharedService, API_BASE_URL) {
    SharedService.authenticate();

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

    $scope.time1 = new Date();
    $scope.time1.setHours(7, 0, 0, 0);
    $scope.time2 = new Date();
    $scope.time2.setHours(8, 0, 0, 0);

    $scope.numberOfSeats = 1;

    $scope.propositions = [];
    $scope.submitForm = function () {
        $scope.showResults = true;

        $http.post(API_BASE_URL + "/search/find-route", {
            departureStopId: $scope.departureStop.id,
            arrivalStopId: $scope.arrivalStop.id
        })
            .then(function (response) {
                $scope.propositions = response.data
                $scope.propositions.forEach(function (proposition) {
                    proposition.showContent = false;
                });

                $timeout(function() {
                    initMap();
                    updateMap($scope.propositions[$scope.currentPropositionIndex].stops);
                }, 0);
            })
            .catch(function (error) {
                console.log("Erreur lors de la récupération des données de recherche d'itinéraire", error);
            });
    };

    $scope.currentPropositionIndex = 0;
    $scope.toggle = function (proposition) {
        proposition.showContent = !proposition.showContent;

        $scope.propositions.forEach(function (prop) {
            if (prop !== proposition)
                prop.showContent = false;
        });
    };

    $scope.showPrevious = function () {
        if ($scope.currentPropositionIndex > 0) {
            $scope.propositions[$scope.currentPropositionIndex].showContent = false;
            $scope.currentPropositionIndex--;
            updateMap($scope.propositions[$scope.currentPropositionIndex].stops);
        }
    };

    $scope.showNext = function () {
        if ($scope.currentPropositionIndex < $scope.propositions.length - 1) {
            $scope.propositions[$scope.currentPropositionIndex].showContent = false;
            $scope.currentPropositionIndex++;
            updateMap($scope.propositions[$scope.currentPropositionIndex].stops);
        }
    };

    function initMap() {
        const map = L.map("map").setView([-18.9064, 47.5246], 8);

        L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
            maxZoom: 19,
        }).addTo(map);

        $scope.map = map;
    }

    function updateMap(stops) {
        if (!$scope.map) return;

        if ($scope.currentLayer) {
            $scope.map.removeLayer($scope.currentLayer);
        }

        stops.forEach(stopGroup => {
            const validStops = stopGroup.filter(stop => stop.latitude !== null && stop.longitude !== null);
            const latlngs = validStops.map(stop => [stop.latitude, stop.longitude]);
            
            const color = SharedService.getRandomColor();

            $scope.currentLayer = L.polyline(latlngs, {color: color}).addTo($scope.map);

            validStops.forEach(stop => {
                L.marker([stop.latitude, stop.longitude]).addTo($scope.map).bindPopup(stop.label);
            });

            if (latlngs.length > 0) $scope.map.fitBounds( L.latLngBounds(latlngs));
        });
    }
}]).controller("NotificationController", ["$scope", "SharedService", function ($scope, SharedService) {
    SharedService.authenticate();

    $scope.notifications = [];

}]).controller("ProfileController", ["SharedService", function(SharedService) {
    SharedService.authenticate();
}]);
