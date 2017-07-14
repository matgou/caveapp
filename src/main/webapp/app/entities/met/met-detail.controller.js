(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MetDetailController', MetDetailController);

    MetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Met', 'Vin'];

    function MetDetailController($scope, $rootScope, $stateParams, previousState, entity, Met, Vin) {
        var vm = this;

        vm.met = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('caveappApp:metUpdate', function(event, result) {
            vm.met = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
