angular.module( 'JavaBasics.app', ['ngRoute', 'ngResource'] )
	.factory(
		'App', function ( $resource ) {
			return $resource(
				'/rest/v1/app/:id', {id: "@id"}, {
					update: {method: 'PUT'}
				}
			);
		}
	)

	.config(
		function ( $routeProvider, $httpProvider ) {
			$routeProvider.when(
				'/app', {
					templateUrl: 'app/apps.html',
					controller: 'app-list'
				}
			).when(
				'/app/create', {
					templateUrl: 'app/app.html',
					controller: 'app-create'
				}
			).when(
				'/app/:id', {
					templateUrl: 'app/app.html',
					controller: 'app-edit'
				}
			);
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		}
	)

	.controller(
		'app-list', function ( $scope, $location, App ) {
			var entities, load = function () {
				entities = App.query(
					function () {
						$scope.entities = entities;
					}
				);
			};
			load();

			$scope.create = function () {
				$location.path( "/app/create" );
			};
			$scope.edit = function ( id ) {
				$location.path( "/app/" + id );
			};
			$scope.delete = function ( id ) {
				App.delete( {id: id}, load );
			};
		}
	)

	.controller(
		'app-create', function ( $scope, $location, App ) {
			var entity = $scope.entity = new App();
			$scope.save = function () {
				App.save(
					entity, function () {
						$location.path( "/app" );
					}
				);
			}
		}
	)

	.controller(
		'app-edit', function ( $scope, $location, $routeParams, App ) {
			var entity = App.get(
				{id: $routeParams.id}, function () {
					$scope.entity = entity;
				}
			);
			$scope.save = function () {
				App.update(
					entity, function () {
						$location.path( "/app" );
					}
				);
			};
		}
	)
;
