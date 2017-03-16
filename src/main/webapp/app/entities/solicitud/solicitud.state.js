(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('solicitud', {
            parent: 'entity',
            url: '/solicitud?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Solicituds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitud/solicituds.html',
                    controller: 'SolicitudController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('solicitud-detail', {
            parent: 'solicitud',
            url: '/solicitud/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Solicitud'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/solicitud/solicitud-detail.html',
                    controller: 'SolicitudDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Solicitud', function($stateParams, Solicitud) {
                    return Solicitud.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'solicitud',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('solicitud-detail.edit', {
            parent: 'solicitud-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitud/solicitud-dialog.html',
                    controller: 'SolicitudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solicitud', function(Solicitud) {
                            return Solicitud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitud.new', {
            parent: 'solicitud',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitud/solicitud-dialog.html',
                    controller: 'SolicitudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero_solicitud: null,
                                fecha: null,
                                hora: null,
                                acto_certificar: null,
                                origen: null,
                                estatus: null,
                                prevencion: null,
                                tiporesponsable: null,
                                nombre_responsable: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('solicitud', null, { reload: 'solicitud' });
                }, function() {
                    $state.go('solicitud');
                });
            }]
        })
        .state('solicitud.edit', {
            parent: 'solicitud',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitud/solicitud-dialog.html',
                    controller: 'SolicitudDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Solicitud', function(Solicitud) {
                            return Solicitud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitud', null, { reload: 'solicitud' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('solicitud.delete', {
            parent: 'solicitud',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/solicitud/solicitud-delete-dialog.html',
                    controller: 'SolicitudDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Solicitud', function(Solicitud) {
                            return Solicitud.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('solicitud', null, { reload: 'solicitud' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
