(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MetDialogController', MetDialogController);

    MetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Met', 'Vin'];

    function MetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Met, Vin) {
        var vm = this;

        vm.met = entity;
        vm.clear = clear;
        vm.save = save;
        vm.vins = Vin.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.met.id !== null) {
                Met.update(vm.met, onSaveSuccess, onSaveError);
            } else {
                Met.save(vm.met, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('caveappApp:metUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
