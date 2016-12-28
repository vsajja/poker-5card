package org.poker

import jooq.generated.tables.pojos.Card

class Hand {
    public static final int REQUIRED_CARDS_PER_HAND = 5

    List<Card> cards = []
    HandType handType

    Map ranks = [:]
    Map suits = [:]

    public Hand(List<Card> cards) {
        this.cards = cards

        assert cards.size() == REQUIRED_CARDS_PER_HAND

        this.handType = evaluate()
    }

    HandType evaluate() {
        ranks = cards.groupBy { Card card -> card.rank }
        suits = cards.groupBy { Card card -> card.suit }

        return HandType.STRAIGHT_FLUSH
    }

    protected boolean isPair() {
        return (ranks.findAll { k, v -> v.size() == 2 }.size() == 1) && !isFullHouse()
    }

    protected boolean isTwoPair() {
        return ranks.findAll { k, v -> v.size() == 2 }.size() == 2
    }

    protected boolean isThreeOfAKind() {
        return (ranks.findAll { k, v -> v.size() == 3 }.size() == 1) && !isFullHouse()
    }

    protected boolean isStraight() {
        def values = cards.collect { it.rank }
        return isSequential(values) && (suits.findAll { k, v -> v.size() == 5 }.size() == 0)
    }

    boolean isSequential(List<Integer> values) {
        boolean isStraight = true

        int ACE_RANK_HIGH = 14
        int ACE_RANK_LOW = 1

        if (values.contains(ACE_RANK_HIGH) && values.containsAll([2, 3, 4, 5])) {
            values.remove((values.indexOf(ACE_RANK_HIGH)))
            values.add(ACE_RANK_LOW)
        }

        values.sort()

        for (int i = 0; i < values.size(); i++) {
            def nextValue = values[i + 1]
            def currentValue = values[i]

            if (nextValue) {
                if (nextValue != currentValue + 1) {
                    isStraight = false
                }
            }
        }
        return isStraight
    }

    protected boolean isFlush() {
        def values = cards.collect { it.rank }
        return !isSequential(values) && (suits.findAll { k, v -> v.size() == 5 }.size() == 1)
    }

    protected boolean isFullHouse() {
        def hasThreeOfAKind = ranks.findAll { k, v -> v.size() == 3 }.size() == 1
        def hasOnePair = ranks.findAll { k, v -> v.size() == 2 }.size() == 1

        return hasThreeOfAKind && hasOnePair
    }

    protected boolean isFourOfAKind() {
        return ranks.findAll { k, v -> v.size() == 4 }.size() == 1
    }

    protected boolean isStraightFlush() {
        def values = cards.collect { it.rank }
        return isSequential(values) && (suits.findAll { k, v -> v.size() == 5 }.size() == 1)
    }

    @Override
    String toString() {
        return cards.collect { it.rankStr + ' of ' + it.suit }.toString()
    }
}