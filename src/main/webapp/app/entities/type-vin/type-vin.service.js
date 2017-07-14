(function() {
    'use strict';
    angular
        .module('caveappApp')
        .factory('TypeVin', TypeVin);

    TypeVin.$inject = ['$resource'];

    function TypeVin ($resource) {
        var resourceUrl =  'api/type-vins/:id';

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
