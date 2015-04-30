vlexxFrontend.controller('TrainController', function ($scope, TrainFactory) {
     $scope.trains = TrainFactory.query();
});

vlexxFrontend.controller('Top10Controller', function ($scope, $routeParams, Top10Factory) {
     $scope.trains = Top10Factory.query({date: $routeParams.date});
});

vlexxFrontend.controller('StatController', function ($scope) {
});
