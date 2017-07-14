(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MetDeleteController',MetDeleteController);

    MetDeleteController.$inject = ['$uibModalInstance', 'entity', 'Met'];

    function MetDeleteController($uibModalInstance, entity, Met) {
        var vm = this;

        vm.met = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Met.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
