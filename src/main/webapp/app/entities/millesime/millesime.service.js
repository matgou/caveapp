(function() {
    'use strict';
    angular
        .module('caveappApp')
        .factory('Millesime', Millesime);

    Millesime.$inject = ['$resource'];

    function Millesime ($resource) {
        var resourceUrl =  'api/millesimes/:id';

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
