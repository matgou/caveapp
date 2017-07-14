(function() {
    'use strict';
    angular
        .module('caveappApp')
        .factory('Vin', Vin);

    Vin.$inject = ['$resource'];

    function Vin ($resource) {
        var resourceUrl =  'api/vins/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
