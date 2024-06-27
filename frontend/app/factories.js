app.factory("UserFactory", ["$window", "$http", "$q", "API_BASE_URL", function($window, $http, $q, API_BASE_URL) {
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
        },
        getNotifications: function (userId) {
            const deferred = $q.defer();

            $http.get(API_BASE_URL + "/user/" + userId + "/notifications")
                .then(function (response) {
                    console.log(response.data);
                    deferred.resolve(response.data);
                })
                .catch(function (error) {
                    console.error("Erreur lors de la récupération des notifications d'un user", error);
                    deferred.reject(error);
                });

            return deferred.promise;
        },
        isAdmin: function (user) {
            return user && user.roleId === 3;
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
}]).factory("BusLineFactory", ["$http", "$q", "API_BASE_URL", function ($http, $q, API_BASE_URL) {
    const factory = {
        lines: [],
        getAll: function () {
            const deferred = $q.defer();

            if (factory.lines.length > 0)
                deferred.resolve(factory.lines);
            else
                $http.get(API_BASE_URL + "/bus-line")
                    .then(function (response) {
                        factory.lines = response.data;
                        deferred.resolve(factory.lines);
                    })
                    .catch(function (error) {
                        console.error("Erreur lors de la récupération des lignes de bus", error);
                        deferred.reject(error);
                    });

            return deferred.promise;
        }
    };

    return factory;
}]);
