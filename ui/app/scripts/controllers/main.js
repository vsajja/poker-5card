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

    var cards = Restangular.all('cards');
    cards.customGET().then(function (cards) {
      $scope.cards = cards;
    });

    $scope.handA = [];
    $scope.handB = [];

    // select handA by default
    $scope.selectedHand = $scope.handA;
    $scope.handAClass = 'selected';

    $scope.selectHand = function (hand) {
      $scope.selectedHand = hand;

      // toggle border to selected hand
      if (hand === $scope.handA) {
        $scope.handAClass = 'selected';
        $scope.handBClass = '';
      }
      else if (hand === $scope.handB) {
        $scope.handBClass = 'selected';
        $scope.handAClass = '';
      }
    };

    $scope.addCard = function (card) {
      var hand = $scope.selectedHand;
      if (hand.length < 5) {
        // check for duplicates before pushing
        if (hand.indexOf(card) === -1) {
          hand.push(card);
        }
        else {
          window.alert('Unable add duplicate card to hand: ' + card.rankStr + ' of ' + card.suit);
        }
      }
    };

    $scope.removeCard = function (hand, card) {
      var index = hand.indexOf(card);
      if (index > -1) {
        hand.splice(index, 1);
      }
    };

    $scope.compareHands = function(handA, handB) {
      $scope.hands = [];
      $scope.hands.push(handA);
      $scope.hands.push(handB);

      Restangular.all('hands').post($scope.hands).then(function (result) {
        $scope.result = result;
      });
    };
  }]);
