(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('TypeVinDetailController', TypeVinDetailController);

    TypeVinDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeVin'];

    function TypeVinDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeVin) {
        var vm = this;

        vm.typeVin = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('caveappApp:typeVinUpdate', function(event, result) {
            vm.typeVin = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
