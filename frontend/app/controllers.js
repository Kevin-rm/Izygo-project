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
            console.log(latlngs);

            validStops.forEach(stop => {
                L.marker([stop.latitude, stop.longitude]).addTo($scope.map).bindPopup(stop.label);
            });

            if (latlngs.length > 0) $scope.map.fitBounds( L.latLngBounds(latlngs));
        });
    }
}]).controller("ReservationController", ["$scope","$http","$rootScope","$location","UserFactory","API_BASE_URL", "SharedService", "ReservationService", "BusLineFactory", function ($scope,$http,$rootScope,$location,UserFactory,API_BASE_URL, SharedService, ReservationService, BusLineFactory) {
    SharedService.authenticate();

    $scope.showResults = false;

    ReservationService.clearData();
    $scope.reservationData = ReservationService.getData();
    
    $scope.busLines = [];
    $scope.stops    = [];
    var bus = {};


    // 
    
    function prix (nb){
        if(nb<=3){
            return 1500;
        }
        if(3<nb && nb <=5 ){
            return 2000;
        }
        if( nb>5){
            return 2500;
        }
    }
   // $scope.limitsOfSeats = $scope.reservationData.numberOfSeats; // Limite des sièges sélectionnables
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

    

    function formatTimestamp(isoDate) {
        var date = new Date(isoDate);
    
        var year = date.getFullYear();
        var month = ('0' + (date.getMonth() + 1)).slice(-2); // Months are zero-indexed
        var day = ('0' + date.getDate()).slice(-2);
        var hours = ('0' + date.getHours()).slice(-2);
        var minutes = ('0' + date.getMinutes()).slice(-2);
        var seconds = ('0' + date.getSeconds()).slice(-2);
        var milliseconds = ('00' + date.getMilliseconds()).slice(-3);
    
        // Format the date parts into the desired format
        var formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}.${milliseconds}`;
        
        return formattedDate;
    }
    $scope.submitForm = function () {
        $scope.showResults = true;
        ReservationService.setData($scope.reservationData);
        var dataSend = {
            departureStopId: $scope.reservationData.departureStop.id,
            dateTime1: formatTimestamp($scope.reservationData.time1),
            dateTime2: formatTimestamp($scope.reservationData.time2) 
        };
        
        var popo = {
            departureStop: $scope.reservationData.departureStop.id,
            arrivalStop : $scope.reservationData.arrivalStop.id,
        }


        $http.post(API_BASE_URL + "/api/book/nbarret", popo)
            .then(function(response){
                console.log(response.data);
                $scope.unitPrice = prix(response.data);
            })
            .catch(function(error) {
            console.error('Error:', error);
            });


        // recherche du bus correspondant
        $http.post(API_BASE_URL + "/api/book/getBus", dataSend)
            .then(function(response) {
                console.log('Response:', response.data);
                bus = response.data;
                getReservationSeat(bus)
            })
            .catch(function(error) {
                console.error('Error:', error);
            });

    };

    


    // Initialisation des variables à partir de reservationData
    $scope.start    = $scope.reservationData.departureStop;
    $scope.arrival  = $scope.reservationData.arrivalStop;
    $scope.isMobile = window.innerWidth < 768;
    $scope.limitsOfSeats = $scope.reservationData.numberOfSeats; // Limite des sièges sélectionnables

    $scope.seats = [];
    $scope.rows = [];
    $scope.selectedSeats = [];
    $scope.reservationList = [];
    ;

    $scope.totalPrice = 0;


    function getReservationSeat(bus){
        var BusAndArrival = {
            busId : bus.busId,
            arrival : $scope.reservationData.departureStop.id
            }
        $http.post("http://localhost:8080/api/book/getReserved", BusAndArrival)
            .then(function (response) {
                console.log('Sièges réservés recuperé avec succès', response.data);
                console.log('busiId:',BusAndArrival.busId+" arriv "+BusAndArrival.arrival);
                $scope.reservationList=response.data;
            })
            .catch(function (error) {
                console.error('Erreur lors de la recuperation des réservation des sièges', error);
            });
    }
    
     
    $scope.seatNumber = 1;

    function createSeat() {
        return {
            number: $scope.seatNumber++, // Initialize seat number
            selected: false
        };
    }

    $scope.isReserved = function (seat) {
        return $scope.reservationList.includes(seat.number);
    }

    $scope.rows = [
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true
        },
        {
            left: [createSeat(), createSeat()],
            right: [],
            aisle: true
        },
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true
        },
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true
        },
        {
            left: [createSeat(), createSeat()],
            right: [createSeat()],
            aisle: true
        },
        {
            left: [createSeat(), createSeat(), createSeat(), createSeat()],
            right: [],
            aisle: false
        }
    ];

    $scope.toggleSelection = function (seat) {
        if (isReserved(seat)) {
            return; // Ne rien faire si le siège est réservé
        }
    
        // Vérifier si le nombre limite de sièges sélectionnés est atteint
        if (!seat.selected && $scope.selectedSeats.length >= $scope.reservationData.numberOfSeats) {
            return; // Ne rien faire si la limite est atteinte
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
        var extartID = [];
        for (var i = 0; i < $scope.selectedSeats.length; i++) {
            extartID.push($scope.selectedSeats[i].number);
        }
        var data = {
            userId : UserFactory.getUser().id,
            busId : bus.busId,
            startStop:$scope.reservationData.departureStop.id,
            endStop :$scope.reservationData.arrivalStop.id,
            seatIds : extartID,
            seatPrice : $scope.unitPrice
        }
        $http.post(API_BASE_URL + "/api/book/bookBus",data)
            .then(function(response){
                console.log(response.data)
                $rootScope.ticketData = response.data;
                $location.path("/reservation-ticket");
            })
            .catch(function(error) {
                console.error('Error:', error);
            });


    };
    
}]).controller('ConfirmationController', ['$scope', '$rootScope', function($scope, $rootScope) {
    // Récupérer les données des tickets depuis $rootScope
    $scope.ticketData = $rootScope.ticketData;
    console.log('Données des tickets pour confirmation:', $scope.ticketData);

    // Convertir les données de ticket en une liste d'images pour l'affichage
    $scope.images = [];
    angular.forEach($scope.ticketData, function(value, key) {
        $scope.images.push({
            src: 'data:image/png;base64,' + value,
            alt: key
        });
    });
    $scope.currentIndex = 0;

    $scope.nextImage = function() {
        $scope.currentIndex = ($scope.currentIndex + 1) % $scope.images.length;
    };

    $scope.prevImage = function() {
        $scope.currentIndex = ($scope.currentIndex - 1 + $scope.images.length) % $scope.images.length;
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

    $scope.user = UserFactory.getUser();
    $scope.activeReservation = [];
    $scope.pastReservation = [];

    function formatDateTime(dateTimeString) {
        const now = new Date(dateTimeString);
        const day = now.getDate().toString().padStart(2, '0');
        const month = (now.getMonth() + 1).toString().padStart(2, '0'); // Les mois commencent à 0 en JavaScript
        const year = now.getFullYear();
        let hours = now.getHours();
        const minutes = now.getMinutes().toString().padStart(2, '0');

        return `${day}-${month}-${year} ${hours} h ${minutes} `;
    }
    
    $http.post(API_BASE_URL + "/Profil/reservation", { id: $scope.user.id })
        .then(function(response) {
            // Handle success
            const reservations = response.data;
            console.log(reservations);
            reservations.forEach(function(reservation) {
                reservation.dateTime  = formatDateTime(reservation.dateTime );
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
                seats.forEach(function(seat) {
                    seat.dateTime = formatDateTime(seat.dateTime);
                });
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
}]).controller("DepositController", ["$scope", "$http", "SharedService", "UserFactory", "API_BASE_URL", function ($scope, $http, SharedService, UserFactory, API_BASE_URL) {
    SharedService.authenticate();

    $scope.data = {
        userId: UserFactory.getUser().id
    };
    $scope.success = null;
    $scope.errors = {};

    $scope.submitForm = function () {
        $http.post(API_BASE_URL + "/user/update-account-balance", $scope.data)
            .then(function (response) {
                $scope.errors = {};
                UserFactory.setUser(response.data);

                $scope.success = "Votre compte a bien été mis à jour";
            })
            .catch(function (error) {
                $scope.success = null;
                if (error.status === 400 && error.data)
                    $scope.errors = error.data;
            });
    };
}]).controller("DashboardController", ["$scope", "$http", "SharedService", "DashboardService", function ($scope, $http, SharedService, DashboardService) {
    SharedService.authenticateAdmin();

    const today = new Date();
    const currentYear = new Date().getFullYear();
    $scope.dateDebut = new Date(currentYear, 0, 1);
    $scope.dateFin = today;

    $scope.chiffreAffaire = 0;  // Default value, replace with API call result
    $scope.reservation = 0;  // Default value, replace with API call result

    DashboardService.getProfitInPeriod($scope.dateDebut, $scope.dateFin)
        .then(function(response) {
            $scope.chiffreAffaire = response.data;
        });

    DashboardService.getCountReservationsInPeriod($scope.dateDebut, $scope.dateFin)
        .then(function(response) {
            $scope.reservation = response.data;
        });

    DashboardService.getProfitThroughYears()
        .then(function(response) {
            console.log(response.data)

            const data = response.data;
            const labels = data.map(item => item.year);
            const values = data.map(item => item.profit);

            initAreaChart1(labels, values);
        });

    DashboardService.getReservationsThroughYears()
        .then(function(response) {
            const data = response.data;
            const labels = data.map(item => item.year);
            const values = data.map(item => item.reservation_count);

            initAreaChart2(labels, values);
        });

    function number_format(number, decimals, dec_point, thousands_sep) {
        number = (number + '').replace(',', '').replace(' ', '');
        var n = !isFinite(+number) ? 0 : +number,
            prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
            sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
            dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
            s = '',
            toFixedFix = function (n, prec) {
                var k = Math.pow(10, prec);
                return '' + Math.round(n * k) / k;
            };
        s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
        if (s[0].length > 3) {
            s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
        }
        if ((s[1] || '').length < prec) {
            s[1] = s[1] || '';
            s[1] += new Array(prec - s[1].length + 1).join('0');
        }
        return s.join(dec);
    }

    // Function to initialize the Pie Chart
    function initPieChart() {
        var ctx = document.getElementById("myPieChart");
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ["Kiosque", "Compte"],
                datasets: [{
                    data: [85, 15],
                    backgroundColor: ['#4e73df', '#1cc88a', '#36b9cc'],
                    hoverBackgroundColor: ['#2e59d9', '#17a673', '#2c9faf'],
                    hoverBorderColor: "rgba(234, 236, 244, 1)",
                }],
            },
            options: {
                maintainAspectRatio: false,
                tooltips: {
                    backgroundColor: "rgb(255,255,255)",
                    bodyFontColor: "#858796",
                    borderColor: '#dddfeb',
                    borderWidth: 1,
                    xPadding: 15,
                    yPadding: 15,
                    displayColors: false,
                    caretPadding: 10,
                },
                legend: {
                    display: false
                },
                cutoutPercentage: 80,
            },
        });
    }

    // Function to initialize the Area Chart for Chiffre d'Affaire
    function initAreaChart1(labels, values) {
        var ctx = document.getElementById("myAreaChart");
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: "Chiffre affaire : ",
                    lineTension: 0.3,
                    backgroundColor: "rgba(78, 115, 223, 0.05)",
                    borderColor: "rgba(78, 115, 223, 1)",
                    pointRadius: 3,
                    pointBackgroundColor: "rgba(78, 115, 223, 1)",
                    pointBorderColor: "rgba(78, 115, 223, 1)",
                    pointHoverRadius: 3,
                    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
                    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
                    pointHitRadius: 10,
                    pointBorderWidth: 2,
                    data: values,
                }],
            },
            options: {
                maintainAspectRatio: false,
                layout: {
                    padding: {
                        left: 10,
                        right: 25,
                        top: 25,
                        bottom: 0
                    }
                },
                scales: {
                    xAxes: [{
                        time: {
                            unit: 'date'
                        },
                        gridLines: {
                            display: false,
                            drawBorder: false
                        },
                        ticks: {
                            maxTicksLimit: 7
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            maxTicksLimit: 5,
                            padding: 10,
                            callback: function (value, index, values) {
                                return 'Ar ' + number_format(value);
                            }
                        },
                        gridLines: {
                            color: "rgb(234, 236, 244)",
                            zeroLineColor: "rgb(234, 236, 244)",
                            drawBorder: false,
                            borderDash: [2],
                            zeroLineBorderDash: [2]
                        }
                    }],
                },
                legend: {
                    display: false
                },
                tooltips: {
                    backgroundColor: "rgb(255,255,255)",
                    bodyFontColor: "#858796",
                    titleMarginBottom: 10,
                    titleFontColor: '#6e707e',
                    titleFontSize: 14,
                    borderColor: '#dddfeb',
                    borderWidth: 1,
                    xPadding: 15,
                    yPadding: 15,
                    displayColors: false,
                    intersect: false,
                    mode: 'index',
                    caretPadding: 10,
                    callbacks: {
                        label: function (tooltipItem, chart) {
                            var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                            return datasetLabel + ' Ar ' + number_format(tooltipItem.yLabel);
                        }
                    }
                }
            }
        });
    }

    // Function to initialize the Area Chart for Clientele
    function initAreaChart2(labels, values) {
        var ctx = document.getElementById("myAreaChart2");
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: "Clientele : ",
                    lineTension: 0.3,
                    backgroundColor: "rgba(78, 115, 223, 0.05)",
                    borderColor: "rgba(78, 115, 223, 1)",
                    pointRadius: 3,
                    pointBackgroundColor: "rgba(78, 115, 223, 1)",
                    pointBorderColor: "rgba(78, 115, 223, 1)",
                    pointHoverRadius: 3,
                    pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
                    pointHoverBorderColor: "rgba(78, 115, 223, 1)",
                    pointHitRadius: 10,
                    pointBorderWidth: 2,
                    data: values,
                }],
            },
            options: {
                maintainAspectRatio: false,
                layout: {
                    padding: {
                        left: 10,
                        right: 25,
                        top: 25,
                        bottom: 0
                    }
                },
                scales: {
                    xAxes: [{
                        time: {
                            unit: 'date'
                        },
                        gridLines: {
                            display: false,
                            drawBorder: false
                        },
                        ticks: {
                            maxTicksLimit: 7
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            maxTicksLimit: 5,
                            padding: 10,
                            callback: function (value, index, values) {
                                return number_format(value);
                            }
                        },
                        gridLines: {
                            color: "rgb(234, 236, 244)",
                            zeroLineColor: "rgb(234, 236, 244)",
                            drawBorder: false,
                            borderDash: [2],
                            zeroLineBorderDash: [2]
                        }
                    }],
                },
                legend: {
                    display: false
                },
                tooltips: {
                    backgroundColor: "rgb(255,255,255)",
                    bodyFontColor: "#858796",
                    titleMarginBottom: 10,
                    titleFontColor: '#6e707e',
                    titleFontSize: 14,
                    borderColor: '#dddfeb',
                    borderWidth: 1,
                    xPadding: 15,
                    yPadding: 15,
                    displayColors: false,
                    intersect: false,
                    mode: 'index',
                    caretPadding: 10,
                    callbacks: {
                        label: function (tooltipItem, chart) {
                            var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                            return datasetLabel + ' ' + number_format(tooltipItem.yLabel);
                        }
                    }
                }
            }
        });
    }

    // Initialize the charts after the controller is fully loaded
    angular.element(document).ready(function () {
        initPieChart();
        initAreaChart1();
        initAreaChart2();
    });
}]).controller("NavbarController", ["$scope", "UserFactory", function ($scope, UserFactory) {
    $scope.isAdmin = UserFactory.isAdmin(UserFactory.getUser());
}]);
