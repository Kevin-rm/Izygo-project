app.factory("UserFactory", function() {
    let user = null;

    return {
        setUser: function(userData) {
            user = userData;
        },
        getUser: function() {
            return user;
        },
        clearUser: function() {
            user = null;
        }
    };
}).factory("BusStopFactory", ["$http", "$q", "API_BASE_URL", function ($http, $q, API_BASE_URL) {
    const factory = {
        stops: [],
        getAll: function () {
            const deferred = $q.defer();

            if (factory.stops.length > 0)
                deferred.resolve(factory.stops);
            else
                $http.get(API_BASE_URL + "/api/arrets-bus")
                    .then(function (response) {
                        factory.stops = response.data;
                        deferred.resolve(factory.stops);
                    })
                    .catch(function (error) {
                        console.error("Erreur lors de la récupération des arrêts de bus", error);
                        deferred.reject(error);
                    });

            return deferred.promise;
        }
    };

    return factory;
}]);
