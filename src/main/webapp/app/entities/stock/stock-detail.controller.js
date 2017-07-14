(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('StockDetailController', StockDetailController);

    StockDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stock', 'Vin'];

    function StockDetailController($scope, $rootScope, $stateParams, previousState, entity, Stock, Vin) {
        var vm = this;

        vm.stock = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('caveappApp:stockUpdate', function(event, result) {
            vm.stock = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
