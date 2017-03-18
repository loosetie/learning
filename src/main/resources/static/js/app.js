angular.module('JavaBasics.app', ['ngRoute', 'ngResource'])
    .factory('App', function ($resource) {
        return $resource('/rest/v1/app/:id');
    })

    .config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/app', {
            templateUrl: 'app/apps.html',
            controller: 'apps'
        }).when('/app/create', {
            templateUrl: 'app/app.html',
            controller: 'create'
        }).when('/app/:id', {
            templateUrl: 'app/app.html',
            controller: 'edit'
        });
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })

    .controller('apps', function ($scope, $location, App) {
        var apps = App.query(function () {
            $scope.apps = apps;
        });
        $scope.create = function () {
            $location.path("/app/create");
        };
        $scope.edit = function (id) {
            $location.path("/app/" + id);
        };
        $scope.delete = function (id) {
            App.delete({id: id});
            $location.path("/app");
        };
    })

    .controller('create', function ($scope, App) {
        $scope.app = new App();
        $scope.save = function () {
            App.save(app);
        }
    })

    .controller('edit', function ($scope, $location, $routeParams, App) {
        var appId = $routeParams.id;
        var app = App.get({id: appId}, function() {
            $scope.app = app;
        });
        $scope.save = function () {
            App.save(app);
        };
    })
;
