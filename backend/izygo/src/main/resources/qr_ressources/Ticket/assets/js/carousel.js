angular.module("carouselApp", []).controller("CarouselController", [
  "$scope",
  "$interval",
  function ($scope, $interval) {
    $scope.images = [
      { src: "../assets/img/Ticket.png", alt: "Ticket 1" },
      { src: "assets/img/TicketWithHoles.png", alt: "Ticket 2" },
      { src: "assets/img/Ticket.png", alt: "Ticket 3" },
    ];

    $scope.currentIndex = 0;

    $scope.nextImage = function () {
      $scope.currentIndex = ($scope.currentIndex + 1) % $scope.images.length;
    };

    $scope.prevImage = function () {
      $scope.currentIndex =
        ($scope.currentIndex - 1 + $scope.images.length) % $scope.images.length;
    };

  },
]);
