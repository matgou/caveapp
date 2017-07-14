(function() {
    'use strict';
    angular
        .module('caveappApp')
        .factory('Met', Met);

    Met.$inject = ['$resource'];

    function Met ($resource) {
        var resourceUrl =  'api/mets/:id';

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
