(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MillesimeDeleteController',MillesimeDeleteController);

    MillesimeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Millesime'];

    function MillesimeDeleteController($uibModalInstance, entity, Millesime) {
        var vm = this;

        vm.millesime = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Millesime.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
