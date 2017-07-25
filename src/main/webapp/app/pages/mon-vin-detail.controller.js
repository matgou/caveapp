(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MonVinDetailController', MonVinDetailController);

    MonVinDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Vin', 'TypeVin', 'Millesime', 'Stock', 'Met'];

    function MonVinDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Vin, TypeVin, Millesime, Stock, Met) {
        var vm = this;

        vm.vin = entity;
        vm.editMode = false;
        vm.isSaving = false;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        vm.millesimes = Millesime.query();
        vm.typevins = TypeVin.query();
        var unsubscribe = $rootScope.$on('caveappApp:vinUpdate', function(event, result) {
            vm.vin = result;
        });
        
        vm.toggleEditMode = toggleEditMode;
        vm.save = save;

        function onSaveSuccess (result) {
            //$scope.$emit('caveappApp:vinUpdate', result);
            vm.isSaving = false;
            vm.editMode = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
        	console.log('ceci est un test');
            vm.isSaving = true;
            if (vm.vin.id !== null) {
                Vin.update(vm.vin, onSaveSuccess, onSaveError);
            } else {
                Vin.save(vm.vin, onSaveSuccess, onSaveError);
            }
        }
        
        function toggleEditMode() {
        	console.log("toggleEditMode");
        	if(vm.editMode) {
        		vm.editMode = false;
        	} else {
        		vm.editMode = true;
        	}
        }
        $scope.$on('$destroy', unsubscribe);
    }
})();
