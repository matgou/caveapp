(function() {
    'use strict';

    angular
        .module('caveappApp')
        .controller('PagesController', PagesController);

    PagesController.$inject = ['DataUtils', 'Vin', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PagesController(DataUtils, Vin, ParseLinks, AlertService, paginationConstants) {

        var vm = this;

        vm.vins = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        vm.next = next;
        vm.prev = prev;
        
        function loadAll () {
            Vin.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                	if ( i == 0 ) {
                		data[i].active = true;
                	} else {
                		data[i].active = false;
                	}
                    vm.vins.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        vm.i_selected = 0;
        
        function next () {
    		vm.vins[vm.i_selected].active = false;
    		if(vm.i_selected <= (vm.vins.length - 2)) {
    			vm.i_selected = vm.i_selected + 1;
    		}
    		vm.vins[vm.i_selected].active = true;
    		console.log("Set active i_selected=" + vm.i_selected + " vm.vins.lenght=" + vm.vins.length);
        };

        function prev () {
    		vm.vins[vm.i_selected].active = false;
        	vm.i_selected = vm.i_selected - 1;
        	if(vm.i_selected < 0) {
        		vm.i_selected = 0;
        	}
    		vm.vins[vm.i_selected].active = true;
        };
        
        function reset () {
            vm.page = 0;
            vm.vins = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
