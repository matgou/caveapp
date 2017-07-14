(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MillesimeDialogController', MillesimeDialogController);

    MillesimeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Millesime'];

    function MillesimeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Millesime) {
        var vm = this;

        vm.millesime = entity;
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
            if (vm.millesime.id !== null) {
                Millesime.update(vm.millesime, onSaveSuccess, onSaveError);
            } else {
                Millesime.save(vm.millesime, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('caveappApp:millesimeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
