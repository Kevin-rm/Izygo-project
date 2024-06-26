app.controller("MainController", ["UserFactory", function (UserFactory) {

}]).controller("LoginController", ["$scope", "$http", "$window", "UserFactory", "API_URL", function($scope, $http, $window, UserFactory, API_URL) {
    $scope.user = {};
    
    $scope.submitForm = function() {
        $http.post(API_URL + "/login", $scope.user)
            .then(function(response) {
                UserFactory.setUser(response.data);
                const userfact=UserFactory.getUser()
                console.log(userfact);
                $window.location.href = "#!/";
            })
            .catch(function(error) {
                    $scope.errors = error.data.message;
            });
    };
}]).controller("SignupController", ["$scope", "$http", "$window", "UserFactory", "API_URL", function($scope, $http, $window, UserFactory, API_URL) {
    $scope.user = {
        roleId: 1 
    };
    $scope.errors = {}
    $scope.name='';

    $scope.submitForm = function() {
        $http.post(API_URL + "/api/user/register", $scope.user)
            .then(function(response) {
                $scope.errors = {};
                $window.location.href = "#!/login";
            })
            .catch(function(error) {
                if (error.status === 400 && error.data)
                    $scope.errors = error.data;
            });
    };
}]).controller("ProfileController", ["$scope", "$http", "$window", "UserFactory","$routeParams",  "API_URL", function($scope, $http, $window, UserFactory) {

    const user = UserFactory.getUser();
    $scope.name='';

    if (!user) {
        $window.location.href = '#!/login';
    } else {
        console.log(user);
        $scope.activeReservations = [];
        $scope.pastReservations = [];
        
        if (user) {
            $http.get('http://localhost:8080/api/profileuser/user/' + user.id)
                .then(function(response) {
                    $scope.name=user;
                    const reservations = response.data;
                    UserFactory.setReservations(reservations);

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
        $scope.showSeats = function(reservation) {
            console.log('Clicked reservation:', reservation);
            $window.location.href = '#!/reservation-active/' + 1 + '/' + reservation.reservationId;
        };
    }
    
}]).controller("ListSeatController", ["$scope", "$http", "$routeParams", function($scope, $http, $routeParams) {
    const userId = $routeParams.userId;
    const reservationId = $routeParams.reservationId;

    $scope.selectedReservationSeats = [];

    if (userId && reservationId) {
        $http.get('http://localhost:8080/api/profileuser/user/' + userId + '/reservation/' + reservationId + '/seats')
            .then(function(response) {
                $scope.selectedReservationSeats = response.data;
            })
            .catch(function(error) {
                console.error('Erreur lors du chargement des sièges réservés :', error);
            });
    } else {
        console.error('userId or reservationId is undefined');
    }
}])
app.controller("ReservationController", ["$scope", "$http", "$window", "$location", "UserFactory", "API_URL", "BusLineFactory", "BusStopFactory","ChoosingSeatFactory", function($scope, $http, $window, $location, UserFactory, API_URL, BusLineFactory, BusStopFactory,ChoosingSeatFactory) {
    $scope.busLines = [];
    $scope.stops = [];
    $scope.selectedLineId = null;
    $scope.depart = null;
    $scope.arrivee = null;

    
    $scope.ticketPrice = 0;

    $scope.getCurrentDateWithTime = function(time) {
        let today = new Date();
        let [hours, minutes] = time.split(':');
        today.setHours(hours);
        today.setMinutes(minutes);
        today.setSeconds(0);
        today.setMilliseconds(0);
        return today;
    };
    var dateTime1;
    var dateTime2;

    // $scope.getCombinedDateTimes = function() {
    //     dateTime1 = $scope.getCurrentDateWithTime($scope.heureDebut);
    //     dateTime2 = $scope.getCurrentDateWithTime($scope.heureArrive);



    // };
    // // getCombinedDateTimes();
    // console.log('DateTime 1:', dateTime1);
    // console.log('DateTime 2:', dateTime2);
    



    // Fonction pour récupérer les lignes de bus
    $scope.getBusLines = function() {
        $http.get(API_URL + "/api/line/busLine")
            .then(function(response) {
                console.log('Lignes de bus récupérées:'+ response.data);
                BusLineFactory.setLine(response.data);
                $scope.busLines = BusLineFactory.getLine();
            })
            .catch(function(error) {
                console.error('Erreur lors de la récupération des lignes de bus:', error);
            });
        };
        
        // Appeler la fonction pour récupérer les lignes de bus dès que le contrôleur est initialisé
    $scope.getBusLines();
    // Fonction pour mettre à jour les arrêts en fonction de la ligne sélectionnée
    $scope.updateStops = function() {
      console.log("line: " + $scope.selectedLineId);
      $http.get(API_URL + "/api/busStop", {
          params: { lineId: $scope.selectedLineId }
      }).then(function(response) {
          console.log('Arrêts de bus récupérés:', response.data);
          BusStopFactory.setStop(response.data);
          $scope.busStop = BusStopFactory.getStop();
      }).catch(function(error) {
          console.error('Erreur lors de la récupération des arrêts de bus:', error);
      });
  };

  
            
            // Appeler la fonction pour récupérer les lignes de bus dès que le contrôleur est initialisé
    $scope.updateStops();

    // Fonction pour traiter la sélection de l'arrêt

    $scope.updatePrice = function() {
        if ($scope.depart && $scope.arrivee) {
            console.log('Calcul du prix pour les arrêts:', $scope.depart.label, $scope.arrivee.label);
            
            // Calcul de la différence entre les IDs de départ et d'arrivée
            let difference = Math.abs($scope.arrivee - $scope.depart);
            
            // Logique de définition du prix en fonction de la différence
            if (difference < 3) {
                $scope.ticketPrice = 1500;
            } else if (difference >= 3 && difference <= 5) {
                $scope.ticketPrice = 2000;
            } else {
                $scope.ticketPrice = 2500;
            }
        }
    };
    
    

      $scope.bookBus = function() {
          const reservationData = {
              userId: $scope.userId,
              busId: $scope.selectedLineId,
              startStopId: $scope.depart,
              endStopId: $scope.arrivee,
              nbSieges: $scope.sieges,
              unitPrice: $scope.ticketPrice,
              heureDepart: $scope.heureDepart,
              heureArrive: $scope.heureArrive,
          };
  
          ChoosingSeatFactory.setReservationData(reservationData);
  
          // Utilisation de $location pour naviguer vers la nouvelle page
          $location.path('/choosingSeat');
      };
  

        // $http.post('http://localhost:8080/api/book/bookBus', reservationData)
        //     .then(function(response) {
                // console.log('Réservation réussie:', response.data);
            // })
            // .catch(function(error) {
            //     console.error('Erreur lors de la réservation:', error);
            //     alert('Erreur lors de la réservation : ' + (error.data ? error.data.message : error.message));
            // });


}])
app.controller("ChoosingSeatController", ["$scope", "$http", "$window", "$location", "UserFactory", "API_URL", "BusLineFactory", "BusStopFactory", "ChoosingSeatFactory", function ($scope, $http, $window, $location, UserFactory, API_URL, BusLineFactory, BusStopFactory, ChoosingSeatFactory) {

    $scope.reservationData = ChoosingSeatFactory.getReservationData();
    console.log("aalp:" + $scope.reservationData.busId);
    // Initialisation des variables à partir de reservationData
    $scope.start = $scope.reservationData.startStopId;
    $scope.arrival = $scope.reservationData.endStopId;
    $scope.isMobile = window.innerWidth < 768;
    $scope.limitsOfSeats = $scope.reservationData.nbSieges; // Limite des sièges sélectionnables

    $scope.seats = [];
    $scope.rows = [];
    $scope.selectedSeats = [];
    $scope.reservationList = [];
    $scope.seatNumber = 1;
    $scope.unitPrice = $scope.reservationData.unitPrice;
    $scope.heureDepart = $scope.reservationData.heureDepart;
    $scope.heureArrive = $scope.reservationData.heureArrive;
    
    let heures = $scope.heureDepart.getHours();
    let minutes = $scope.heureDepart.getMinutes();
    let secondes = $scope.heureDepart.getSeconds();
    let millisecondes = $scope.heureDepart.getMilliseconds();
    
    let today1 = new Date();

    // Définit l'heure de l'objet `today1` avec les valeurs extraites
    today1.setHours($scope.heureDepart.getHours(),  $scope.heureDepart.getMinutes(), $scope.heureDepart.getSeconds(), $scope.heureDepart.getMilliseconds());
    
    // Assigne cette nouvelle date à `$scope.heureDepart`
    $scope.heureDepart = today1;
    
    let today2 = new Date();

    // Définit l'heure de l'objet `today2` avec les valeurs extraites
    today2.setHours($scope.heureArrive.getHours(),  $scope.heureArrive.getMinutes(), $scope.heureArrive.getSeconds(), $scope.heureArrive.getMilliseconds());
    
    // Assigne cette nouvelle date à `$scope.heureDepart`
    $scope.heureDepart = today2;

    console.log("sart::"+$scope.start);
    const dateTimeReservation={
        departureStopId:$scope.start,
        dateTime1:today1,
        dateTime2:today2,
    };
    
    console.log("day 1:"+today1);
    console.log("day 2:"+today2);
    //maka busId
    $http.post("http://localhost:8080/api/book/getBus", dateTimeReservation)
    .then(function (response) {
        console.log("busiiid:"+response.data[0]);
        // $scope.busId=response.data[0];

        // console.log('Sièges réservés recuperé avec succès', response.data);
        // console.log('busiId:',BusAndArrival.busId+" arriv "+BusAndArrival.arrival);
        // $scope.reservationList=response.data;
    })
    .catch(function (error) {
        console.error('Fanina be ah ehh', error);
    });


    $scope.numeroBus = $scope.reservationData.busId; // Numéro du bus
    const BusAndArrival={
        busId:$scope.numeroBus,
        arrival:$scope.arrival,
    };



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

}]);

// Contenu du fichier app.js
