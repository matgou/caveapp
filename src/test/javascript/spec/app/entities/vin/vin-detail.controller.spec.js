'use strict';

describe('Controller Tests', function() {

    describe('Vin Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVin, MockTypeVin, MockMillesime, MockStock, MockMet, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVin = jasmine.createSpy('MockVin');
            MockTypeVin = jasmine.createSpy('MockTypeVin');
            MockMillesime = jasmine.createSpy('MockMillesime');
            MockStock = jasmine.createSpy('MockStock');
            MockMet = jasmine.createSpy('MockMet');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Vin': MockVin,
                'TypeVin': MockTypeVin,
                'Millesime': MockMillesime,
                'Stock': MockStock,
                'Met': MockMet,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("VinDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'caveappApp:vinUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
