package org.poker

import jooq.generated.tables.pojos.Card
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
public class HandSpec extends Specification {

    def "get hand type high card: #hand"() {
        expect:
        hand.handType == HandType.HIGH_CARD

        where:
        hand << createHands(HandType.HIGH_CARD)
    }

    def "get hand type pair: #hand"() {
        expect:
        hand.handType == HandType.PAIR

        where:
        hand << createHands(HandType.PAIR)
    }

    def "get hand type two pair: #hand"() {
        expect:
        hand.handType == HandType.TWO_PAIR

        where:
        hand << createHands(HandType.TWO_PAIR)
    }

    def "get hand type three of a kind: #hand"() {
        expect:
        hand.handType == HandType.THREE_OF_A_KIND

        where:
        hand << createHands(HandType.THREE_OF_A_KIND)
    }

    def "get hand type straight: #hand"() {
        expect:
        hand.handType == HandType.STRAIGHT

        where:
        hand << createHands(HandType.STRAIGHT)
    }

    def "get hand type flush: #hand"() {
        expect:
        hand.handType == HandType.FLUSH

        where:
        hand << createHands(HandType.FLUSH)
    }

    def "get hand type full house: #hand"() {
        expect:
        hand.handType == HandType.FULL_HOUSE

        where:
        hand << createHands(HandType.FULL_HOUSE)
    }

    def "get hand type four of a kind: #hand"() {
        expect:
        hand.handType == HandType.FOUR_OF_A_KIND

        where:
        hand << createHands(HandType.FOUR_OF_A_KIND)
    }

    def "get hand type straight flush: #hand"() {
        expect:
        hand.handType == HandType.STRAIGHT_FLUSH

        where:
        hand << createHands(HandType.STRAIGHT_FLUSH)
    }

    def "is pair: #hand"() {
        expect:
        hand.isPair() == true

        where:
        hand << createHands(HandType.PAIR)
    }

    def "not pair: #hand"() {
        expect:
        hand.isPair() == false

        where:
        hand << createHands((HandType.values() - HandType.PAIR).toList())
    }

    def "two pair: #hand"() {
        expect:
        hand.isTwoPair() == true

        where:
        hand << createHands(HandType.TWO_PAIR)
    }

    def "not two pair: #hand"() {
        expect:
        hand.isTwoPair() == false

        where:
        hand << createHands((HandType.values() - HandType.TWO_PAIR).toList())
    }

    def "three of a kind: #hand"() {
        expect:
        hand.isThreeOfAKind() == true

        where:
        hand << createHands(HandType.THREE_OF_A_KIND)

    }

    def "not three of a kind: #hand"() {
        expect:
        hand.isThreeOfAKind() == false

        where:
        hand << createHands((HandType.values() - HandType.THREE_OF_A_KIND).toList())
    }

    def "straight: #hand"() {
        expect:
        hand.isStraight() == true

        where:
        hand << createHands(HandType.STRAIGHT)
    }

    def "not straight: #hand"() {
        expect:
        hand.isStraight() == false

        where:
        hand << createHands((HandType.values() - HandType.STRAIGHT).toList())
    }

    def "flush: #hand"() {
        expect:
        hand.isFlush() == true

        where:
        hand << createHands(HandType.FLUSH)
    }

    def "not flush: #hand"() {
        expect:
        hand.isFlush() == false

        where:
        hand << createHands((HandType.values() - HandType.FLUSH).toList())
    }

    def "full house: #hand"() {
        expect:
        hand.isFullHouse() == true

        where:
        hand << createHands(HandType.FULL_HOUSE)
    }

    def "not full house: #hand"() {
        expect:
        hand.isFullHouse() == false

        where:
        hand << createHands((HandType.values() - HandType.FULL_HOUSE).toList())
    }

    def "four of a kind: #hand"() {
        expect:
        hand.isFourOfAKind() == true

        where:
        hand << createHands(HandType.FOUR_OF_A_KIND)
    }

    def "not four of a kind: #hand"() {
        expect:
        hand.isFourOfAKind() == false

        where:
        hand << createHands((HandType.values() - HandType.FOUR_OF_A_KIND).toList())
    }

    def "straight flush: #hand"() {
        expect:
        hand.isStraightFlush() == true

        where:
        hand << createHands(HandType.STRAIGHT_FLUSH)
    }

    def "not straight flush: #hand"() {
        expect:
        hand.isStraightFlush() == false

        where:
        hand << createHands((HandType.values() - HandType.STRAIGHT_FLUSH).toList())
    }

    List<Hand> createHands(HandType hand) {
        return createHands([hand])
    }

    List<Hand> createHands(List<HandType> types) {
        List<Hand> hands = []

        types.each { HandType type ->
            switch (type) {
                case HandType.HIGH_CARD:
                    hands << createHand('2clubs 3spades 5hearts 6diamonds 7clubs')
                    hands << createHand('8spades 9clubs 10diamonds jackdiamonds acehearts')
                    break;
                case HandType.PAIR:
                    hands << createHand('2clubs 2spades 3hearts 4diamonds 5clubs')
                    hands << createHand('8clubs 9spades 10hearts jackdiamonds jackclubs')
                    break;
                case HandType.TWO_PAIR:
                    hands << createHand('2clubs 2spades 4hearts 4diamonds 5clubs')
                    hands << createHand('8clubs 10spades 10hearts acediamonds aceclubs')
                    break;
                case HandType.THREE_OF_A_KIND:
                    hands << createHand('2clubs 2spades 2hearts 4diamonds 5clubs')
                    hands << createHand('8clubs 10spades 10hearts 10diamonds jackclubs')
                    break;
                case HandType.STRAIGHT:
                    hands << createHand('aceclubs 2spades 3hearts 4diamonds 5clubs')
                    hands << createHand('9clubs 10spades jackhearts queendiamonds kingclubs')
                    hands << createHand('10clubs jackspades queenhearts kingdiamonds aceclubs')
                    break;
                case HandType.FLUSH:
                    hands << createHand('aceclubs 2clubs 3clubs 4clubs 6clubs')
                    hands << createHand('2diamonds 6diamonds 9diamonds 4diamonds jackdiamonds')
                    break;
                case HandType.FULL_HOUSE:
                    hands << createHand('2clubs 2spades 2hearts 4diamonds 4clubs')
                    hands << createHand('jackdiamonds 10spades 10hearts 10diamonds jackclubs')
                    break;
                case HandType.FOUR_OF_A_KIND:
                    hands << createHand('2clubs 2spades 2hearts 2diamonds 4clubs')
                    hands << createHand('4clubs jackclubs jackspades jackhearts jackdiamonds')
                    break;
                case HandType.STRAIGHT_FLUSH:
                    hands << createHand('aceclubs 2clubs 3clubs 4clubs 5clubs')
                    hands << createHand('9spades 10spades jackspades queenspades kingspades')
                    hands << createHand('10diamonds jackdiamonds queendiamonds kingdiamonds acediamonds')
                    break;
                default:
                    break;
            }
        }
        return hands
    }

    /**
     * Creates a 5-card poker hand. @see {@link org.poker.Hand}
     * @param handStr
     * example: '2clubs 2spades 2hearts 2diamonds 3clubs'
     * @return
     */
    Hand createHand(String handStr) {
        def suits = ['clubs',
                     'spades',
                     'hearts',
                     'diamonds']
        Map ranks = [
                '2'    : 2,
                '3'    : 3,
                '4'    : 4,
                '5'    : 5,
                '6'    : 6,
                '7'    : 7,
                '8'    : 8,
                '9'    : 9,
                '10'   : 10,
                'jack' : 11,
                'queen': 12,
                'king' : 13,
                'ace'  : 14
        ]

        List<Card> cards = []

        if (handStr) {
            def cardArray = handStr.split(' ')
            cardArray.each { String cardStr ->
                suits.each { String suit ->
                    if (cardStr.contains(suit)) {
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