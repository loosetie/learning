angular.module( 'JavaBasics.tpl', ['ngRoute', 'ngResource', 'isteven-multi-select'] )
	.factory(
		'Template', function ( $resource ) {
			return $resource(
				'/rest/v1/tpl/:id', {id: "@id"}, {
					update: {method: 'PUT'}
				}
			);
		}
	)

	.config(
		function ( $routeProvider, $httpProvider ) {
			$routeProvider.when(
				'/tpl', {
					templateUrl: 'tpl/tpls.html',
					controller: 'tpl-list'
				}
			).when(
				'/tpl/create', {
					templateUrl: 'tpl/tpl.html',
					controller: 'tpl-create'
				}
			).when(
				'/tpl/:id', {
					templateUrl: 'tpl/tpl.html',
					controller: 'tpl-edit'
				}
			);
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		}
	)

	.controller(
		'tpl-list', function ( $scope, $location, Template ) {
			var entities, load = function () {
				entities = Template.query(
					function () {
						$scope.entities = entities;
					}
				);
			};
			load();

			$scope.create = function () {
				$location.path( "/tpl/create" );
			};
			$scope.edit = function ( id ) {
				$location.path( "/tpl/" + id );
			};
			$scope.delete = function ( id ) {
				Template.delete( {id: id}, load );
			};
		}
	)

	.controller(
		'tpl-create', function ( $scope, $location, Template, App ) {
			var entity = $scope.entity = new Template();
			var apps = App.query(
				function () {
					$scope.apps = apps;
				}
			);
			$scope.save = function () {
				Template.save(
					entity, function () {
						$location.path( "/tpl" );
					}
				);
			}
		}
	)

	.controller(
		'tpl-edit', function ( $scope, $location, $routeParams, Template, App ) {
			var ids = [], entity = Template.get(
				{id: $routeParams.id}, function () {
					$scope.entity = entity;
					for( var i = 0; i < entity.apps.length; i++ ) {
						ids[entity.apps[i].id] = true;
					}
				}
			);
			var apps = App.query(
				function () {
					for( var i = 0; i < apps.length; i++ ) {
						var app = apps[i];
						app.sel = !!ids[app.id];
					}
					$scope.apps = apps;
				}
			);
			$scope.save = function () {
				Template.update(
					entity, function () {
						$location.path( "/tpl" );
					}
				);
			};
		}
	)
;
