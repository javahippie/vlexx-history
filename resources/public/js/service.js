vlexxFrontend.factory('TrainFactory', function($resource) {
    return $resource('/trains/all', {}, {
        query: {method: 'GET', isArray: true}
    });
});
