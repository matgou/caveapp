(function() {
    'use strict';

    angular
        .module('caveappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vin', {
            parent: 'entity',
            url: '/vin',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vins'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vin/vins.html',
                    controller: 'VinController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('vin-detail', {
            parent: 'vin',
            url: '/vin/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vin'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vin/vin-detail.html',
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
        .state('vin-detail.edit', {
            parent: 'vin-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vin/vin-dialog.html',
                    controller: 'VinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vin', function(Vin) {
                            return Vin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vin.new', {
            parent: 'vin',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vin/vin-dialog.html',
                    controller: 'VinDialogController',
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
        })
        .state('vin.edit', {
            parent: 'vin',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vin/vin-dialog.html',
                    controller: 'VinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vin', function(Vin) {
                            return Vin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vin', null, { reload: 'vin' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vin.delete', {
            parent: 'vin',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vin/vin-delete-dialog.html',
                    controller: 'VinDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vin', function(Vin) {
                            return Vin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vin', null, { reload: 'vin' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
