vlexxFrontend.factory('TrainFactory', function($resource) {
    return $resource('http://localhost:3000/trains/all', {}, {
        query: {method: 'GET', isArray: true}
    });
});