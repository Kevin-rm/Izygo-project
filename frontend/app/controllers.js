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
}]).controller("RouteSearchController", ["$scope", "$http", "$timeout", "BusStopFactory", "SharedService", "ReservationService", "API_BASE_URL", function ($scope, $http, $timeout, BusStopFactory, SharedService, ReservationService, API_BASE_URL) {
    SharedService.authenticate();
    $scope.showResults = false;

    ReservationService.clearData();
    $scope.reservationData = ReservationService.getData();

    $scope.stops = [];
    BusStopFactory.getAll()
        .then(function (data) {
            $scope.stops = data;
        })
        .catch(function (error) {
            console.error(error);
        });

    $scope.excludeSelectedArrival = function(stop) {
        return SharedService.excludeSelectedArrival(stop, $scope.reservationData.arrivalStop);
    };

    $scope.excludeSelectedDeparture = function(stop) {
        return SharedService.excludeSelectedDeparture(stop, $scope.reservationData.departureStop);
    };

    $scope.propositions = [];
    $scope.submitForm = function () {
        $scope.showResults = true;

        $http.post(API_BASE_URL + "/search/find-route", {
            departureStopId: $scope.reservationData.departureStop.id,
            arrivalStopId: $scope.reservationData.arrivalStop.id
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
    $scope.start    = $scope.reservationData.departureStop.id;
    $scope.arrival  = $scope.reservationData.arrivalStop.id;
    $scope.isMobile = window.innerWidth < 768;
    $scope.limitsOfSeats = $scope.reservationData.numberOfSeats; // Limite des sièges sélectionnables

    $scope.seats = [];
    $scope.rows = [];
    $scope.selectedSeats = [];
    $scope.reservationList = [];
    $scope.seatNumber = 1;

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
    function isReserved(seat) {
        return $scope.reservationList.includes(seat.number);
    }

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
        if (isReserved(seat)) {
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

    // Validation des sièges sélectionnés
    $scope.validate = function () {

    };
}]).controller("NotificationController", ["$scope", "$http", "$interval", "API_BASE_URL", "SharedService", "UserFactory", function ($scope, $http, $interval, API_BASE_URL, SharedService, UserFactory) {
    SharedService.authenticate();

    $scope.notifications = [];
    function fetchNotifications() {
        const user = UserFactory.getUser();
        if (user && user.id) {
            UserFactory.getNotifications(user.id).then(function(notifications) {
                $scope.notifications = notifications;
            }, function(error) {
                console.error(error);
            });
        }
    }

    let fetchInterval = $interval(fetchNotifications, 10000);

    $scope.$on("$destroy", function() {
        if (angular.isDefined(fetchInterval)) {
            $interval.cancel(fetchInterval);
            fetchInterval = undefined;
        }
    });
    fetchNotifications();

    $scope.acceptNotification = function(notification) {
        $http.post(API_BASE_URL + "/notifications/accept", notification)
            .then(function(response) {
                console.log(response)
            }, function(error) {
                console.error("Erreur lors de l'acceptation de la notification", error);
            });
    };

    $scope.declineNotification = function(notification) {
        $http.post(API_BASE_URL + "/notifications/decline", notification)
            .then(function(response) {
                console.log(response)
            }, function(error) {
                console.error("Erreur lors du refus de la notification", error);
            });
    };
}]).controller("ProfileController", ["$scope","$http","$location","UserFactory","API_BASE_URL","SharedService","ProfileSeatsActiveServices", function($scope,$http,$location,UserFactory,API_BASE_URL,SharedService,ProfileSeatsActiveServices) {
    SharedService.authenticate();

    const user = UserFactory.getUser();
    var userId = user.id;
    $scope.firstname = user.firstname;
    $scope.lastname = user.lastname;
    $scope.activeReservation = [];
    $scope.pastReservation = [];
    
    
    $http.post(API_BASE_URL + "/Profil/reservation", { id: userId })
        .then(function(response) {
            // Handle success
            var reservations = response.data;
            console.log(reservations);
            reservations.forEach(function(reservation) {
                if(reservation.isActive){
                    $scope.activeReservation.push(reservation);
                }
                else{
                    $scope.pastReservation.push(reservation);
                }
            });
            
        })
        .catch(function(error) {
            // Handle error
            console.error("Error fetching reservations:", error);
        });

        $scope.getReservationSeatActive = function(id){
            $http.post(API_BASE_URL + "/Profil/myseat", { reservationId: id })
            .then(function(response) {
                // Handle success
                var seats = response.data;
                ProfileSeatsActiveServices.setData(seats);
                
                $location.path("/reservation-active");
            })
            .catch(function(error) {
                // Handle error
                console.error("Error fetching reservations:", error);
            });
        }    
}]).controller("ProfileSeatsController", ["$scope","$location", "SharedService", "ProfileSeatsActiveServices", function($scope,$location, SharedService, ProfileSeatsActiveServices) {
    // Authenticate the user
    SharedService.authenticate();

    // Get reservation data
    $scope.seats = ProfileSeatsActiveServices.getData();
    console.log($scope.seats);

    $scope.annuler = function(seats_id){
        ProfileSeatsActiveServices.setSeat(seats_id);
        $location.path('/annulation');
    }
}]).controller("annulationController", ["$scope","$http", "$location","API_BASE_URL", "SharedService", "ProfileSeatsActiveServices", function($scope,$http, $location,API_BASE_URL, SharedService, ProfileSeatsActiveServices) {
    // Authenticate the user
    SharedService.authenticate();

    $scope.reservationSeatId = ProfileSeatsActiveServices.getSeat();
    console.log($scope.reservationSeatId);

    if($scope.reservationSeatId ==null){
        $location.path("/profil");
    }
    $scope.annulationConfirmer = function(id){
        $http.post(API_BASE_URL + "/cancel", { reservationSeatId: id })
            .then(function() {
                $location.path("/");
            })
            .catch(function(error) {
                // Handle error
                console.error("Error fetching reservations:", error);
            });
    }
}]);
