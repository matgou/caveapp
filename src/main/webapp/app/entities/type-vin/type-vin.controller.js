(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('TypeVinController', TypeVinController);

    TypeVinController.$inject = ['TypeVin'];

    function TypeVinController(TypeVin) {

        var vm = this;

        vm.typeVins = [];

        loadAll();

        function loadAll() {
            TypeVin.query(function(result) {
                vm.typeVins = result;
                vm.searchQuery = null;
            });
        }
    }
})();
