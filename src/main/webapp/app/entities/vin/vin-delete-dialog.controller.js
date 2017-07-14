(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('VinDeleteController',VinDeleteController);

    VinDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vin'];

    function VinDeleteController($uibModalInstance, entity, Vin) {
        var vm = this;

        vm.vin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Vin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
