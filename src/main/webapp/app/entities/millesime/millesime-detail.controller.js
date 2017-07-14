(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MillesimeDetailController', MillesimeDetailController);

    MillesimeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Millesime'];

    function MillesimeDetailController($scope, $rootScope, $stateParams, previousState, entity, Millesime) {
        var vm = this;

        vm.millesime = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('caveappApp:millesimeUpdate', function(event, result) {
            vm.millesime = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
