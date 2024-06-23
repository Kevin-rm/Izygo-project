app.service("SharedService", [function () {
    this.excludeSelectedArrival = function(stop, arrivalStop) {
        return !arrivalStop || stop.id !== arrivalStop.id;
    };

    this.excludeSelectedDeparture = function(stop, departureStop) {
        return !departureStop || stop.id !== departureStop.id;
    };

    this.getRandomColor = function () {
        const letters = '0123456789ABCDEF';

        let color = '#';
        for (let i = 0; i < 6; i++)
            color += letters[Math.floor(Math.random() * 16)];

        return color;
    };
}]);