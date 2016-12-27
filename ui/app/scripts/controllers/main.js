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
    var cards = Restangular.all('cards');

    poker.customGET().then(function (poker) {
      $scope.poker = poker;
    });

    cards.customGET().then(function (cards) {
      $scope.cards = cards;
    });

    $scope.handA = [];
    $scope.handB = [];

    $scope.addCard = function (card) {
      $scope.handA.push(card);
      console.log($scope.handA);
    };
  }]);
