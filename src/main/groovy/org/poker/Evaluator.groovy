package org.poker

/**
 * This class provides the ability to compare two 5-card poker hands.
 *
 * @author vsajja
 */
public class Evaluator {
    /**
     * Compares two 5-card poker hands, handA and handB
     *
     * @param handA
     * @param handB
     */
    def evaluate(Hand handA, Hand handB) {
        assert handA
        assert handB

        HandType handAType = handA.handType
        HandType handBType = handB.handType

        int handAOrdinal = handAType.ordinal()
        int handBOrdinal = handBType.ordinal()

        String winner = 'Hand A'

        if(handAOrdinal > handBOrdinal) {
            winner = 'Hand A'
        }
        else if(handAOrdinal < handBOrdinal) {
            winner = 'Hand B'
        }
        else if(handAOrdinal == handBOrdinal) {
            winner = 'Draw'
        }

        def result = ['winner': winner, 'handA': handA.toString(), 'handB': handB.toString() ]

        return result
    }
}
