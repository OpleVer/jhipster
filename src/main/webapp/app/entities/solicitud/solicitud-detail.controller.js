(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('SolicitudDetailController', SolicitudDetailController);

    SolicitudDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Solicitud'];

    function SolicitudDetailController($scope, $rootScope, $stateParams, previousState, entity, Solicitud) {
        var vm = this;

        vm.solicitud = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:solicitudUpdate', function(event, result) {
            vm.solicitud = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
