(function() {
    'use strict';

    angular
        .module('caveappApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('millesime', {
            parent: 'entity',
            url: '/millesime',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Millesimes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/millesime/millesimes.html',
                    controller: 'MillesimeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('millesime-detail', {
            parent: 'millesime',
            url: '/millesime/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Millesime'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/millesime/millesime-detail.html',
                    controller: 'MillesimeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Millesime', function($stateParams, Millesime) {
                    return Millesime.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'millesime',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('millesime-detail.edit', {
            parent: 'millesime-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/millesime/millesime-dialog.html',
                    controller: 'MillesimeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Millesime', function(Millesime) {
                            return Millesime.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('millesime.new', {
            parent: 'millesime',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/millesime/millesime-dialog.html',
                    controller: 'MillesimeDialogController',
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
                    $state.go('millesime', null, { reload: 'millesime' });
                }, function() {
                    $state.go('millesime');
                });
            }]
        })
        .state('millesime.edit', {
            parent: 'millesime',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/millesime/millesime-dialog.html',
                    controller: 'MillesimeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Millesime', function(Millesime) {
                            return Millesime.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('millesime', null, { reload: 'millesime' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('millesime.delete', {
            parent: 'millesime',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/millesime/millesime-delete-dialog.html',
                    controller: 'MillesimeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Millesime', function(Millesime) {
                            return Millesime.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('millesime', null, { reload: 'millesime' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
