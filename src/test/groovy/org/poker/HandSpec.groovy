package org.poker

import jooq.generated.tables.pojos.Card
import spock.lang.Specification
import spock.lang.Unroll

public class HandSpec extends Specification {
    def cleanupSpec() {

    }

    @Unroll
    def "is four of a kind: #hand"() {
        expect:
        hand.isFourOfAKind() == true

        where:
        hand << [
                createHand('2clubs 2spades 2hearts 2diamonds 3clubs'),
                createHand('3clubs 3spades 3hearts 3diamonds 9clubs'),
                createHand('aceclubs acespades acehearts acediamonds jackclubs')]
    }

    @Unroll
    def "is not four of a kind: #hand"() {
        expect:
        hand.isFourOfAKind() == false

        where:
        hand << [
                createHand('2clubs 2spades 2hearts 3diamonds 3clubs'),
                createHand('2clubs 3spades 4hearts 5diamonds 6clubs'),
                createHand('aceclubs acespades acehearts jackdiamonds jackclubs')]
    }

    /**
     * Creates a 5-card poker hand. @see {@link org.poker.Hand}
     * @param handStr
     * example: '2clubs 2spades 2hearts 2diamonds 3clubs'
     */
    Hand createHand(handStr) {
        Map suits = [ 'C' : 'clubs',
                      'S' : 'spades',
                      'H' : 'hearts',
                      'D' : 'diamonds']
        Map ranks = [
                '2' : 2,
                '3' : 3,
                '4' : 4,
                '5' : 5,
                '6' : 6,
                '7' : 7,
                '8' : 8,
                '9' : 9,
                '10': 10,
                'jack' : 11,
                'queen' : 12,
                'king' : 13,
                'ace' : 14
        ]

        List<Card> cards = []

        if(handStr) {
            def cardArray = handStr.split(' ')
            cardArray.each { String cardStr ->
                suits.each { String suit ->
                    if(cardStr.contains(suit)) {
                        def rankStr = cardStr - suit
                        def name = rankStr + '_of_' + suit

                        Card card = new Card(null, name, ranks[rankStr.toLowerCase()], rankStr, suit, null)
                        cards.add(card)
                    }
                }
            }
        }

        Hand hand = new Hand(cards)
        return hand
    }
}