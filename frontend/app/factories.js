angular.module("izygoApp").factory("UserFactory", function() {
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
});
