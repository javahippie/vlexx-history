vlexxFrontend.controller('TrainController', function ($scope, TrainFactory) {
     $scope.trains = TrainFactory.query();
});