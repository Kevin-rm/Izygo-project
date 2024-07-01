app.service( "SharedService", ["$location", "UserFactory", function ($location, UserFactory) {
    this.authenticate = function () {
        if (UserFactory.getUser()) return;

        $location.path("/login");
    }

    this.authenticateAdmin = function () {
        const user = UserFactory.getUser();
        if (!UserFactory.isAdmin(user))
            $location.path("/login");
    }

    this.excludeSelectedArrival = function(stop, arrivalStop) {
        return !arrivalStop || stop.id !== arrivalStop.id;
    };

    this.excludeSelectedDeparture = function(stop, departureStop) {
        return !departureStop || stop.id !== departureStop.id;
    };

    this.getRandomColor = function () {
        const letters = "0123456789ABCDEF";

        let color = "#";
        for (let i = 0; i < 6; i++)
            color += letters[Math.floor(Math.random() * 16)];

        return color;
    };
}]).service("ReservationService", function() {
    const time1 = new Date();
    time1.setHours(7, 0, 0, 0);
    const time2 = new Date();
    time2.setHours(8, 0, 0, 0);

    let data = {
        selectedLine: null,
        departureStop: null,
        arrivalStop: null,
        time1: time1,
        time2: time2,
        numberOfSeats: 1
    };

    return {
        getData: function() {
            return data;
        },
        setData: function(reservationData) {
            data = reservationData;
        },
        clearData: function() {
            data = {
                selectedLine: null,
                departureStop: null,
                arrivalStop: null,
                time1: time1,
                time2: time2,
                numberOfSeats: 1
            };
        }
    };
}).service("ProfileSeatsActiveServices", function() {
    var reservationData = [];  
    var seat = null;

    this.setData = function(data) {
        reservationData = data;
    };

    this.getData = function() {
        return reservationData;
    };

    this.setSeat = function(seatid){
        seat = seatid;
    };

    this.getSeat = function(){
        return seat;
    }
}).service("DashboardService", ["$http", "API_BASE_URL", function($http, API_BASE_URL) {
    this.getProfitInPeriod = function(dateMin, dateMax) {
        return $http.get(API_BASE_URL + "/dashboard/profitInPeriod", {
            params: { dateMin: dateMin, dateMax: dateMax }
        });
    };

    this.getProfitThroughYears = function() {
        return $http.get(API_BASE_URL + "/dashboard/profitThroughYears");
    };

    this.getCountReservationsInPeriod = function(dateMin, dateMax) {
        return $http.get(API_BASE_URL + "/dashboard/countReservationsInPeriod", {
            params: { dateMin: dateMin, dateMax: dateMax }
        });
    };

    this.getReservationsThroughYears = function() {
        return $http.get(API_BASE_URL + "/dashboard/reservationsThroughYears");
    };
}]);
