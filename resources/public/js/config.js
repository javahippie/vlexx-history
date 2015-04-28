var vlexxFrontend = angular.module('vlexxFrontend', ['ngResource', 'ngRoute']);

vlexxFrontend.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/all', {
        templateUrl: 'partials/all.html',
        controller: 'TrainController'
      }).
      when('/top10/:date', {
        templateUrl: 'partials/top10.html',
        controller: 'Top10Controller'
      }).
      when('/wtf', {
        templateUrl: 'partials/wtf.html',
      }).
      otherwise({
        redirectTo: '/all'
      });
  }]);
