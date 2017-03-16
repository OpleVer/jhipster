(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('SolicitudDialogController', SolicitudDialogController);

    SolicitudDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Solicitud'];

    function SolicitudDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Solicitud) {
        var vm = this;

        vm.solicitud = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.solicitud.id !== null) {
                Solicitud.update(vm.solicitud, onSaveSuccess, onSaveError);
            } else {
                Solicitud.save(vm.solicitud, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:solicitudUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;
        vm.datePickerOpenStatus.hora = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
