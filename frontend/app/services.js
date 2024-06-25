app.service( "SharedService", ["$location", "UserFactory", function ($location, UserFactory) {
    this.authenticate = function () {
        if (UserFactory.getUser()) return;

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
});
