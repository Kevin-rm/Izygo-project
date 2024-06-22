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
