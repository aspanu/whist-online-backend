package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.Hand

data class HandDealt(val hands: List<Hand>, val trumpCard: Card?)

class DeckHelper {
    private val maxHandSize = 8
    private val maxCardMagnitude = 13

    fun shuffleDeck(numPlayers: Int): List<Card> {
        // Find the smallest card necessary to include in the deck
        val numberOfTypesOfCards = (numPlayers * maxHandSize) / 4
        val minimumCardNeeded = maxCardMagnitude - numberOfTypesOfCards

        // Add cards to deck
        val deck = mutableListOf<Card>()
        for (card in Card.values()) {
            if (card.magnitude > minimumCardNeeded) {
                deck.add(card)
            }
        }

        // Shuffle deck
        return deck.shuffled()
    }

    fun dealHand(deck: List<Card>, numPlayers: Int, numCards: Int): HandDealt {
        // Create a list of hands and deal cards into them
        val mutableDeck = deck.map { it }.toMutableList()
        val hands = mutableListOf<Hand>()
        for (i in 0 until numPlayers) {
            hands.add(Hand(List(numCards) { mutableDeck.removeLast() }))
        }

        val trumpCard = mutableDeck.removeLastOrNull()
        return HandDealt(hands, trumpCard)
    }

}