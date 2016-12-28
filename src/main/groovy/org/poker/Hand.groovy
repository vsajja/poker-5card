package org.poker

import jooq.generated.tables.pojos.Card

class Hand {
    List<Card> cards = []
    HandType handType

    public Hand(List<Card> cards) {
        this.cards = cards
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
        return ranks.findAll { k, v -> v.size() == 4 }.size() == 1
    }
}