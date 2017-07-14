(function() {
    'use strict';

    angular
        .module('caveappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-vin', {
            parent: 'entity',
            url: '/type-vin',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeVins'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-vin/type-vins.html',
                    controller: 'TypeVinController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-vin-detail', {
            parent: 'type-vin',
            url: '/type-vin/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeVin'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-vin/type-vin-detail.html',
                    controller: 'TypeVinDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeVin', function($stateParams, TypeVin) {
                    return TypeVin.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-vin',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-vin-detail.edit', {
            parent: 'type-vin-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-vin/type-vin-dialog.html',
                    controller: 'TypeVinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeVin', function(TypeVin) {
                            return TypeVin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-vin.new', {
            parent: 'type-vin',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-vin/type-vin-dialog.html',
                    controller: 'TypeVinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-vin', null, { reload: 'type-vin' });
                }, function() {
                    $state.go('type-vin');
                });
            }]
        })
        .state('type-vin.edit', {
            parent: 'type-vin',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-vin/type-vin-dialog.html',
                    controller: 'TypeVinDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeVin', function(TypeVin) {
                            return TypeVin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-vin', null, { reload: 'type-vin' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-vin.delete', {
            parent: 'type-vin',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-vin/type-vin-delete-dialog.html',
                    controller: 'TypeVinDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeVin', function(TypeVin) {
                            return TypeVin.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-vin', null, { reload: 'type-vin' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
