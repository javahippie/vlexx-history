vlexxFrontend.factory('TrainFactory', function($resource) {
    return $resource('/trains/all', {}, {
        query: {method: 'GET', isArray: true}
    });
});

vlexxFrontend.factory('Top10Factory', function($resource) {
  return $resource('/trains/top10/:date', { date:'@_data' }, {
        query: {method: 'GET', isArray: true}
  });
});
