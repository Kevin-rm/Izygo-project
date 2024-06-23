app.controller("MainController", ["UserFactory", function (UserFactory) {

    console.log(UserFactory.getUser())
}]).controller("LoginController", ["$scope", "$http", "$window", "UserFactory", "API_URL", function($scope, $http, $window, UserFactory, API_URL) {
    $scope.user = {};

    $scope.submitForm = function() {
        $http.post(API_URL + "/login", $scope.user)
            .then(function(response) {
                UserFactory.setUser(response.data);
                $window.location.href = "#!/";
            })
            .catch(function(error) {
                console.error(error);
            });
    };
}]).controller("SignupController", ["$scope", "$http", "$window", "UserFactory", "API_URL", function($scope, $http, $window, UserFactory, API_URL) {
    $scope.user = {
        roleId: 1 // Client
    };
    // $scope.success = null;
    $scope.errors = {}

    $scope.submitForm = function() {
        $http.post(API_URL + "/api/user/register", $scope.user)
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
}])
app.controller("ReservationController", ["$scope", "$http", "$window", "$location", "UserFactory", "API_URL", "BusLineFactory", "BusStopFactory","ChoosingSeatFactory", function($scope, $http, $window, $location, UserFactory, API_URL, BusLineFactory, BusStopFactory,ChoosingSeatFactory) {
    $scope.busLines = [];
    $scope.stops = [];
    $scope.selectedLineId = null;
    $scope.depart = null;
    $scope.arrivee = null;
    $scope.heureDebut = "12:00";
    $scope.heureFin = "13:00";
    // $scope.nombreSieges = 1;
    $scope.ticketPrice = 0;

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
app.controller("ChoosingSeatController",["$scope", "$http", "$window", "$location", "UserFactory", "API_URL", "BusLineFactory", "BusStopFactory","ChoosingSeatFactory", function($scope, $http, $window, $location, UserFactory, API_URL, BusLineFactory, BusStopFactory,ChoosingSeatFactory){

  $scope.reservationData = ChoosingSeatFactory.getReservationData();
  console.log("aalp:"+$scope.reservationData.busId);
  // Initialisation des variables à partir de reservationData
  $scope.start = $scope.reservationData.startStopId;
  $scope.arrival = $scope.reservationData.endStopId;
  $scope.isMobile = window.innerWidth < 768;
  $scope.limitsOfSeats = $scope.reservationData.nbSieges; // Limite des sièges sélectionnables
  $scope.numeroBus = $scope.reservationData.busId; // Numéro du bus
  $scope.seats = [];
  $scope.rows = [];
  $scope.selectedSeats = [];
  $scope.reservationList = [];
  $scope.seatNumber = 1;
  $scope.unitPrice = $scope.reservationData.unitPrice;

//   $scope.getActiveReservation = function() {
//     $http.get(API_URL + "/api/activeReservations").then(function(response) {
//         console.log('reservation avtive de bus récupérés:', response.data);
//         ReservationFactory.setReservation(response.data);
//         $scope.reservationList = ReservationFactory.getReservation().id;
//     }).catch(function(error) {
//         console.error('Erreur lors de la récupération des arrêts de bus:', error);
//     });
// };
// getActiveReservation();

  $scope.totalPrice = 0;

  const reservationSeatData = {
    userId: $scope.reservationData.userId,
    busId: $scope.reservationData.busId,
    startStopId: $scope.start,
    endStopId: $scope.arrival,
    seatIds: $scope.selectedSeats,
};

console.log("aaa:"+$scope.arrival);
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
      // 1. Vérifie si le nombre de sièges sélectionnés est inférieur à la limite autorisée
      // OU si le siège est déjà sélectionné (dans ce cas, on permet la désélection).
      if (
          $scope.selectedSeats.length < $scope.limitsOfSeats ||
          seat.selected
      ) {
          // 2. Inverse l'état de sélection du siège.
          seat.selected = !seat.selected;
          // 3. Si le siège est maintenant sélectionné :
          if (seat.selected) {
              // a. Ajoute le siège à la liste des sièges sélectionnés.
              $scope.selectedSeats.push(seat);
              // b. Met à jour le prix total basé sur le nombre de sièges sélectionnés.
              $scope.totalPrice =
                  $scope.unitPrice * $scope.selectedSeats.length;
          // 4. Si le siège est maintenant désélectionné :
          } else {
              // a. Trouve l'index du siège dans la liste des sièges sélectionnés.
              var index = $scope.selectedSeats.indexOf(seat);
              // b. Si le siège est trouvé dans la liste (index différent de -1).
              if (index !== -1) {
                  // c. Supprime le siège de la liste des sièges sélectionnés.
                  $scope.selectedSeats.splice(index, 1);
                  // d. Met à jour le prix total basé sur le nombre de sièges sélectionnés.
                  $scope.totalPrice =
                      $scope.unitPrice * $scope.selectedSeats.length;
              }
          }
      }
  };

  $scope.validerSeat = function() {

      $http.post(API_URL + "/api/book/bookBus",reservationSeatData)
      .then(function(response) {
        console.log('Arrêts de bus récupérés:', response.data);
    }).catch(function(error) {
        console.error('Erreur lors de la récupération des arrêts de bus:', error);
    });    
      // Logique de validation ou traitement à effectuer
      console.log('Bouton Valider cliqué !');

    // Vous pouvez ajouter d'autres actions ici en fonction de votre besoin
  };

}]);
// Contenu du fichier app.js









