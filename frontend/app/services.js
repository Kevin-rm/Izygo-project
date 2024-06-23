app.service("SharedService", [function () {
    this.excludeSelectedArrival = function(stop, arrivalStop) {
        return !arrivalStop || stop.id !== arrivalStop.id;
    };

    this.excludeSelectedDeparture = function(stop, departureStop) {
        return !departureStop || stop.id !== departureStop.id;
    };
}]);