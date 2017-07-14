(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('VinDetailController', VinDetailController);

    VinDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Vin', 'TypeVin', 'Millesime', 'Stock', 'Met'];

    function VinDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Vin, TypeVin, Millesime, Stock, Met) {
        var vm = this;

        vm.vin = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('caveappApp:vinUpdate', function(event, result) {
            vm.vin = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
