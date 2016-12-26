'use strict';

/**
 * @ngdoc function
 * @name pokerApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the pokerApp
 */
angular.module('pokerApp')
  .controller('MainCtrl', ['$scope', 'Restangular', function ($scope, Restangular) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    var poker = Restangular.all('poker');

    // This will query /poker and return a promise.
    poker.customGET().then(function (poker) {
      $scope.poker = poker;
    });
  }]);
