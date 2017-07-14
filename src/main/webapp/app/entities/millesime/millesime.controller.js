(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('MillesimeController', MillesimeController);

    MillesimeController.$inject = ['Millesime'];

    function MillesimeController(Millesime) {

        var vm = this;

        vm.millesimes = [];

        loadAll();

        function loadAll() {
            Millesime.query(function(result) {
                vm.millesimes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
