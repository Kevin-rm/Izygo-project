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
}]).service("WebSocketService", ["$rootScope", function($rootScope) {
    let stompClient = null;
    const socket = new SockJS("/ws");

    this.connect = function(userId) {
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame) {
            stompClient.subscribe("/user/" + userId + "/queue/notifications", function(notification) {
                $rootScope.$broadcast("newNotification", JSON.parse(notification.body));
            });
        });
    };

    this.disconnect = function() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        console.log("Déconnecté");
    };

    this.send = function(message) {
        stompClient.send("/app/sendNotification", {}, JSON.stringify(message));
    };
}]);
