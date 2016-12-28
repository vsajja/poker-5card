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

        Hand winner = handA

        if(handAOrdinal > handBOrdinal) {
            winner = handA
        }
        else if(handAOrdinal < handBOrdinal) {
            winner = handB
        }
        else if(handAOrdinal == handBOrdinal) {
            winner = null
        }

        def result = ['winner': winner, 'handA': handA, handAType: handA.handType, 'handB': handB, 'handBType': handB.handType]

        return result
    }
}
