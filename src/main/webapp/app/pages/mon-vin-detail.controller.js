(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MonVinDetailController', MonVinDetailController);

    MonVinDetailController.$inject = ['$scope', '$rootScope', '$http', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Vin', 'TypeVin', 'Millesime', 'Stock', 'Met'];

    function MonVinDetailController($scope, $rootScope, $http, $stateParams, previousState, DataUtils, entity, Vin, TypeVin, Millesime, Stock, Met) {
        var vm = this;

        vm.vin = entity;
        vm.editMode = false;
        vm.isSaving = false;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        vm.millesimes = Millesime.query();
        Stock.queryFromVin({id: entity.id}, function(result) { vm.stock=result.pop(); console.log(vm.stock)});
        
        vm.typevins = TypeVin.query();
        var unsubscribe = $rootScope.$on('caveappApp:vinUpdate', function(event, result) {
            vm.vin = result;
        });
        
        vm.toggleEditMode = toggleEditMode;
        vm.save = save;
        vm.increaseStock = increaseStock;
        vm.decreaseStock = decreaseStock;
        
        function decreaseStock() {
        	vm.isSaving = true;
        	if(vm.stock != null) {
        		if(vm.stock.nbBouteille >= 1) {
        			vm.stock.nbBouteille = vm.stock.nbBouteille - 1;
        		}
        		Stock.update(vm.stock);
        	} else {
        		vm.stock = {};
        		vm.stock.nbBouteille = 0;
        		vm.stock.vin = vm.vin;
        		
        		vm.stock = Stock.save(vm.stock, onStockSaveSucess, onStockSaveError);
        	}
        }

        function increaseStock() {
    		console.log('Begin increaseStock');
    		console.log(vm.stock);
        	vm.isSaving = true;
        	if(vm.stock != null) {
        		vm.stock.nbBouteille = vm.stock.nbBouteille + 1;
        		Stock.update(vm.stock);
        	} else {
        		vm.stock = {};
        		vm.stock.nbBouteille = 1;
        		vm.stock.vin = vm.vin;
        		console.log(vm.stock);
        		vm.stock = Stock.save(vm.stock, onStockSaveSucess, onStockSaveError);
        	}        	
        }
        function onStockSaveSucess(result) {
        	vm.stock = result;
        	vm.isSaving = false;
            vm.editMode = false;
            console.log(result);
        }
        function onStockSaveError() {
        	vm.isSaving = false;
            vm.editMode = false;
        }
        
        function onSaveSuccess (result) {
            //$scope.$emit('caveappApp:vinUpdate', result);
            vm.isSaving = false;
            vm.editMode = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.setDefaultPhotoEtiquette = function(vin) {
        	$http.get("content/images/bottle_tag.pgg", {responseType: "blob"})
            .then(function(blob){
            	console.log(blob);
            	DataUtils.toBase64(blob.data, function(base64Data) {
                    $scope.$apply(function() {
                        vm.vin.photoEtiquette = base64Data;
                        vm.vin.photoEtiquetteContentType = 'image/jpeg';
                    });
                });
            });
        };
        
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
