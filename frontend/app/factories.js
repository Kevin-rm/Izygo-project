angular.module("izygoApp").factory("UserFactory", function() {
    let user = null;
    let reservations = null;

    return {
        setUser: function(userData) {
            user = userData;
        },
        getUser: function() {
            return user;
        },
        clearUser: function() {
            user = null;
            reservations = null; 
        },
        setReservations: function(reservationsData) {
            reservations = reservationsData;
        },
        getReservations: function() {
            return reservations;
        },
        hasReservations: function() {
            return reservations !== null;
        }
    };
});

app.factory("BusStopFactory", function() {
    let Stop = null;

    return {
        setStop: function(StopData) {
            Stop = StopData;
        },
        getStop: function() {
            return Stop;
        },
        clearStop: function() {
            Stop = null;
        }
    };
});

app.factory("ReservationFactory", function() {
    let Reservation = null;

    return {
        setReservation: function(ReservationData) {
            Reservation = ReservationData;
        },
        getReservation: function() {
            return Reservation;
        },
        clearReservation: function() {
            Reservation = null;
        }
    };
});

app.factory("BusLineFactory", function() {
        let Line = null;
    
        return {
            setLine: function(LineData) {
                Line = LineData;
            },
            getLine: function() {
                return Line;
            },
            clearLine: function() {
                Line = null;
            }
        };
});


 app.factory("ChoosingSeatFactory", function() {
        let reservationData = {
            userId: null,
            busId: null,
            startStopId: null,
            endStopId: null,
            nbSieges: null,
            unitPrice:null,
        };
    
        return {
            setReservationData: function(data) {
                reservationData = data;
            },
            getReservationData: function() {
                return reservationData;
            },
            clearReservationData: function() {
                reservationData = {
                    userId: null,
                    busId: null,
                    startStopId: null,
                    endStopId: null,
                    nbSieges: null,
                    unitPrice: null
                };
            }
        };
    });
    
    
