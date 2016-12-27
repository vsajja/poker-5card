'use strict';

/**
 * @ngdoc overview
 * @name pokerApp
 * @description
 * # pokerApp
 *
 * Main module of the application.
 */
angular
  .module('pokerApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'restangular'
  ])
  .config(function ($routeProvider, $compileProvider, RestangularProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .otherwise({
        redirectTo: '/'
      });

    // release
    // $compileProvider.debugInfoEnabled(false);
    // RestangularProvider.setBaseUrl('/api/v1');

    // dev
    RestangularProvider.setBaseUrl('http://localhost:5050/api/v1');
  });
