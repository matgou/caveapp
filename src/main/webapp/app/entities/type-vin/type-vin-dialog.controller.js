(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('TypeVinDialogController', TypeVinDialogController);

    TypeVinDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeVin'];

    function TypeVinDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeVin) {
        var vm = this;

        vm.typeVin = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeVin.id !== null) {
                TypeVin.update(vm.typeVin, onSaveSuccess, onSaveError);
            } else {
                TypeVin.save(vm.typeVin, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('caveappApp:typeVinUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
