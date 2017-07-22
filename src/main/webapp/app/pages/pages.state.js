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
    	});
    	$stateProvider.state('monvin_detail', {
    		parent: 'app',
            url: '/mon_vin/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vin'
            },
            views: {
                'content@': {
                    templateUrl: 'app/pages/vin-detail.html',
                    controller: 'VinDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Vin', function($stateParams, Vin) {
                    return Vin.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vin',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
    	})
    	.state('mes_vins.new', {
            parent: 'mes_vins',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/pages/mes-vins-dialog.html',
                    controller: 'MesVinsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                appellation: null,
                                cuvee: null,
                                cepage: null,
                                region: null,
                                domaine: null,
                                temperature: null,
                                tauxAlcool: null,
                                codeBare: null,
                                anneeDebut: null,
                                anneeFin: null,
                                description: null,
                                photoEtiquette: null,
                                photoEtiquetteContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vin', null, { reload: 'vin' });
                }, function() {
                    $state.go('vin');
                });
            }]
        });
    }
})();
