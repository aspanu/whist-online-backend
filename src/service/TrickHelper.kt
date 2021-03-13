package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.Trick
import kotlin.math.abs

class TrickHelper {

    fun trickWinner(trick: Trick): Player {
        // Figure out what the trump suit is, and then see the biggest card in the trump suit or suit first played

        val trumpSuit = trick.trumpCard?.suit
        val firstCardSuit = trick.playerCards[trick.playerOrder[0]]?.suit
            ?: throw IllegalArgumentException("Couldn't find card played.")

        var highestCard: Card = Card.TWO_CLUBS
        var firstCard = true

        trick.playerOrder.forEach{
            val cardPlayed = trick.playerCards[it] ?: throw IllegalArgumentException("Couldn't find card played.")
            if (firstCard) {
                highestCard = cardPlayed
                firstCard = false
            } else if (cardPlayed.suit == firstCardSuit && highestCard.suit != trumpSuit) {
                if (cardPlayed.magnitude > highestCard.magnitude) highestCard = cardPlayed
            } else if (cardPlayed.suit == trumpSuit) {
                highestCard = if (highestCard.suit == firstCardSuit) cardPlayed
                else if (highestCard.suit == trumpSuit && cardPlayed.magnitude > highestCard.magnitude) cardPlayed
                else throw IllegalArgumentException("We played a trump and the highest card was neither the trump or highest card suits.")
            }
        }

        return trick.playerCards.filter { it.value == highestCard }.keys.first()
    }

    // Input in order of 'Bid' and 'Tricks'
    fun calculateScoreChanges(playerBidsTricks: Map<Player, Pair<Int, Int>>): Map<Player, Int> {
        val scoreHelper = ScoreHelper()
        // Go through every player, evaluate the amount of tricks they made compared to their bid, take the difference
        // and send it to the ScoreHelper to get the amount of score they made.

        return playerBidsTricks.mapValues {
            scoreHelper.calculateScore(it.value.first, abs(it.value.first - it.value.second))
        }
    }
}