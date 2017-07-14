(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('TypeVinDeleteController',TypeVinDeleteController);

    TypeVinDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeVin'];

    function TypeVinDeleteController($uibModalInstance, entity, TypeVin) {
        var vm = this;

        vm.typeVin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeVin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
