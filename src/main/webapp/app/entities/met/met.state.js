(function() {
    'use strict';

    angular
        .module('caveappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('met', {
            parent: 'entity',
            url: '/met',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/met/mets.html',
                    controller: 'MetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('met-detail', {
            parent: 'met',
            url: '/met/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Met'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/met/met-detail.html',
                    controller: 'MetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Met', function($stateParams, Met) {
                    return Met.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'met',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('met-detail.edit', {
            parent: 'met-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/met/met-dialog.html',
                    controller: 'MetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Met', function(Met) {
                            return Met.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('met.new', {
            parent: 'met',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/met/met-dialog.html',
                    controller: 'MetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categorie: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('met', null, { reload: 'met' });
                }, function() {
                    $state.go('met');
                });
            }]
        })
        .state('met.edit', {
            parent: 'met',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/met/met-dialog.html',
                    controller: 'MetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Met', function(Met) {
                            return Met.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('met', null, { reload: 'met' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('met.delete', {
            parent: 'met',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/met/met-delete-dialog.html',
                    controller: 'MetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Met', function(Met) {
                            return Met.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('met', null, { reload: 'met' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
