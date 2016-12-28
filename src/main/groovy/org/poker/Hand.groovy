package org.poker

import jooq.generated.tables.pojos.Card

class Hand {
    public static final int REQUIRED_CARDS_PER_HAND = 5

    List<Card> cards = []
    HandType handType

    public Hand(List<Card> cards) {
        this.cards = cards

        assert cards.size() == REQUIRED_CARDS_PER_HAND

        this.handType = evaluate()
    }

    HandType evaluate() {
        return HandType.STRAIGHT_FLUSH
    }

    public boolean isStraight() {

    }

    public boolean isFlush() {
        Map suits = cards.groupBy { Card card -> card.suit }
        return suits.findAll { k, v -> v.size() == 5 }.size() == 1
    }

    public boolean isFourOfAKind() {
        Map ranks = cards.groupBy { Card card -> card.rank }
        println ranks
        return ranks.findAll { k, v -> v.size() == 4 }.size() == 1
    }

    @Override
    String toString() {
        return cards.collect { it.rankStr + ' of ' + it.suit }.toString()
    }
}