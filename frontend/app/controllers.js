app.controller("LandingPageController", ["$scope", "SharedService", "UserFactory", function ($scope, SharedService, UserFactory) {
    SharedService.authenticate();

    const user = UserFactory.getUser();
    if (user) {
        $scope.firstname = user.firstname;
        $scope.lastname = user.lastname;
    }
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
}]).controller("ReservationController", ["$scope", "SharedService", function ($scope, SharedService) {
    SharedService.authenticate();

    $scope.showResults = false;
    $scope.$on("reservationFormSubmitted", function () {
        $scope.showResults = true;
    });
}]).controller("ReservationFormController", ["$scope", "SharedService", "ReservationService", "BusLineFactory", function ($scope, SharedService, ReservationService, BusLineFactory) {
    ReservationService.clearData();

    $scope.reservationData = ReservationService.getData();

    $scope.busLines = [];
    $scope.stops    = [];

    BusLineFactory.getAll()
        .then(function (data) {
            $scope.busLines = data;
        })
        .catch(function (error) {
            console.error(error);
        });

    $scope.updateStops = function () {
        if ($scope.reservationData.selectedLine) {
            $scope.stops = $scope.reservationData.selectedLine.stops;
            $scope.reservationData.departureStop = null;
            $scope.reservationData.arrivalStop   = null;
        } else {
            $scope.stops = [];
        }
    };

    $scope.excludeSelectedArrival = function(stop) {
        return SharedService.excludeSelectedArrival(stop, $scope.reservationData.arrivalStop);
    };

    $scope.excludeSelectedDeparture = function(stop) {
        return SharedService.excludeSelectedDeparture(stop, $scope.reservationData.departureStop);
    };

    $scope.submitForm = function () {
        $scope.$emit("reservationFormSubmitted");
        ReservationService.setData($scope.reservationData);
    };
}]).controller("SeatSelectionController", ["$scope", "$http", "ReservationService", function ($scope, $http, ReservationService) {
    $scope.reservationData = ReservationService.getData();

    // Initialisation des variables à partir de reservationData
    $scope.start = $scope.reservationData.startStopId;
    $scope.arrival = $scope.reservationData.endStopId;
    $scope.isMobile = window.innerWidth < 768;
    $scope.limitsOfSeats = $scope.reservationData.nbSieges; // Limite des sièges sélectionnables
    $scope.numeroBus = $scope.reservationData.busId; // Numéro du bus
    const BusAndArrival={
        busId:$scope.numeroBus,
        arrival:$scope.arrival,
    };
    $scope.seats = [];
    $scope.rows = [];
    $scope.selectedSeats = [];
    $scope.reservationList = [];
    $scope.seatNumber = 1;
    $scope.unitPrice = $scope.reservationData.unitPrice;
    $scope.heureDebut = $scope.heureDepart;
    $scope.heureFin = $scope.heureArrive;
    console.log("izyyy:"+$scope.heureDebut);

    $scope.totalPrice = 0;

    $http.post("http://localhost:8080/api/book/getReserved", BusAndArrival)
        .then(function (response) {

            console.log('Sièges réservés recuperé avec succès', response.data);
            console.log('busiId:',BusAndArrival.busId+" arriv "+BusAndArrival.arrival);
            $scope.reservationList=response.data;
        })
        .catch(function (error) {
            console.error('Erreur lors de la recuperation des réservation des sièges', error);
        });


    // Fonction pour créer un siège
    function createSeat() {
        return {
            number: $scope.seatNumber++,
            selected: false,
        };
    }

    // Fonction pour vérifier si un siège est réservé
    $scope.isReserved = function (seat) {
        return $scope.reservationList.includes(seat.number);
    };

    // Configurer les sièges
    $scope.frontSeat1 = createSeat();

    $scope.rows = [
        // Rangée 2
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true,
        },
        // Rangée 3
        {
            left: [createSeat(), createSeat()],
            right: [],
            aisle: true,
        },
        // Rangée 4
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true,
        },
        // Rangée 5
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true,
        },
        // Rangée 6
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true,
        },
        // Rangée 7
        {
            left: [createSeat(), createSeat(), createSeat(), createSeat()],
            right: [],
            aisle: false,
        },
    ];

    $scope.toggleSelection = function (seat) {
        if ($scope.isReserved(seat)) {
            return; // Ne rien faire si le siège est réservé
        }

        // Inverse l'état de sélection du siège
        seat.selected = !seat.selected;

        if (seat.selected) {
            // Ajoute le siège à la liste des sièges sélectionnés
            $scope.selectedSeats.push(seat);
        } else {
            // Retire le siège de la liste des sièges sélectionnés s'il est désélectionné
            var index = $scope.selectedSeats.indexOf(seat);
            if (index !== -1) {
                $scope.selectedSeats.splice(index, 1);
            }
        }

        // Met à jour le prix total basé sur le nombre de sièges sélectionnés
        $scope.totalPrice = $scope.unitPrice * $scope.selectedSeats.length;
    };

    // Fonction pour extraire les numéros de siège à partir de $scope.selectedSeats
    function extractSeatNumbers() {
        var seatNumbers = [];
        for (var i = 0; i < $scope.selectedSeats.length; i++) {
            seatNumbers.push($scope.selectedSeats[i].number);
        }
        return seatNumbers;
    }

    // Validation des sièges sélectionnés
    $scope.validerSeat = function () {
        var seatNumbers = extractSeatNumbers(); // Extraire les numéros de siège actuels

        const reservationSeatData = {
            userId: 1, // Placeholder pour l'ID de l'utilisateur
            busId: $scope.reservationData.busId,
            startStopId: $scope.start,
            endStopId: $scope.arrival,
            seatIds: seatNumbers, // Utilisation des numéros de siège extraits
        };

        console.log("Reservation seat data:", reservationSeatData);

        // Appel POST HTTP pour réserver les sièges
        $http.post(API_URL + "/api/book/bookBus", reservationSeatData)
            .then(function (response) {
                console.log('Sièges réservés avec succès', response.data);
            })
            .catch(function (error) {
                console.error('Erreur lors de la réservation des sièges', error);
            });

        // Autres logiques ou traitements à effectuer après la validation
        console.log('Bouton Valider cliqué !');
        console.log("Seat numbers:", seatNumbers);
        console.log('aaaaa:'+reservationSeatData.endStopId);
    };
}]).controller("NotificationController", ["$scope", "SharedService", function ($scope, SharedService) {
    SharedService.authenticate();

    $scope.notifications = [];

}]).controller("ProfileController", ["SharedService", function(SharedService) {
    SharedService.authenticate();
}]);
