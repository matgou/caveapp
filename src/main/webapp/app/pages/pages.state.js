(function() {
    'use strict';

    angular
        .module('caveappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
    	$stateProvider.state('mes_vins', {
    		parent: 'app',
    		url: '/mes_vins',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mes vins'
            },
            views: {
                'content@': {
                    templateUrl: 'app/pages/mes_vins.html',
                    controller: 'PagesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
    	}
    		);
    }
})();
