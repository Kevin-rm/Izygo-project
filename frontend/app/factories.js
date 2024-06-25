app.factory("UserFactory", ["$window", function($window) {
    const userKey = "user";

    return {
        getUser: function() {
            const user = $window.sessionStorage.getItem(userKey);
            return user ? JSON.parse(user) : null;
        },
        setUser: function(user) {
            $window.sessionStorage.setItem(userKey, JSON.stringify(user));
        },
        clearUser: function() {
            $window.sessionStorage.removeItem(userKey);
        }
    };
}]).factory("BusStopFactory", ["$http", "$q", "API_BASE_URL", function ($http, $q, API_BASE_URL) {
    const factory = {
        stops: [],
        getAll: function () {
            const deferred = $q.defer();

            if (factory.stops.length > 0)
                deferred.resolve(factory.stops);
            else
                $http.get(API_BASE_URL + "/arrets-bus")
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
