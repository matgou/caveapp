(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('VinDialogController', VinDialogController);

    VinDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Vin', 'TypeVin', 'Millesime', 'Stock', 'Met', 'User'];

    function VinDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Vin, TypeVin, Millesime, Stock, Met, User) {
        var vm = this;

        vm.vin = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.typevins = TypeVin.query();
        vm.millesimes = Millesime.query();
        vm.stocks = Stock.query();
        vm.mets = Met.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vin.id !== null) {
                Vin.update(vm.vin, onSaveSuccess, onSaveError);
            } else {
                Vin.save(vm.vin, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('caveappApp:vinUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPhotoEtiquette = function ($file, vin) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        vin.photoEtiquette = base64Data;
                        vin.photoEtiquetteContentType = $file.type;
                    });
                });
            }
        };

    }
})();
