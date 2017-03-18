angular.module( 'JavaBasics.basic', ['ngRoute'] )
	.config(
		function ( $routeProvider, $httpProvider ) {
			$routeProvider.when(
				'/', {
					templateUrl: 'home.html',
					controller: 'home'
				}
			).when(
				'/login', {
					templateUrl: 'login.html',
					controller: 'navigation'
				}
			).otherwise( '/' );
			$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		}
	)

	.controller(
		'home', function ( $scope, $http ) {
			$http.get( '/resource/' ).then(
				function ( data ) {
					$scope.greeting = data.data;
				}, function () {
				}
			)
		}
	)

	.controller(
		'navigation', function ( $rootScope, $scope, $http, $location ) {
			var authenticate = function ( credentials, callback ) {
				var headers = credentials ? {
						authorization: "Basic "
						+ btoa( credentials.username + ":" + credentials.password )
					} : {};

				$http.get( 'user', {headers: headers} ).then(
					function ( data ) {
						$rootScope.authenticated = !!data.data.name;
						$rootScope.user = data.data;
						callback && callback();
					}, function () {
						$rootScope.authenticated = false;
						callback && callback();
					}
				);
			};

			authenticate();
			$scope.credentials = {};
			$scope.login = function () {
				authenticate(
					$scope.credentials, function () {
						if( $rootScope.authenticated ) {
							$location.path( "/" );
							$scope.error = false;
						}
						else {
							$location.path( "/login" );
							$scope.error = true;
						}
					}
				);
			};

			$scope.logout = function () {
				$http.post( 'logout', {} ).then(
					function ( data ) {
						$rootScope.authenticated = false;
						$location.path( "/" );
					}, function ( data ) {
						$rootScope.authenticated = false;
					}
				);
			}

			$scope.active = function ( path, strict ) {
				var p = $location.path();
				if( !strict ) {
					p = p.substr( 0, path.length );
				}
				return p === path ? 'active' : '';
			}
		}
	)
;
