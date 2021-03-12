package service

import com.aspanu.whistOnline.service.DeckHelper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

class DeckHelperTest {

    @Test
    fun deckWith1Player() {
        val deckHelper = DeckHelper()

        val deck = deckHelper.shuffleDeck(1)

        assertEquals(8, deck.size, "Should have gotten 8 cards back, instead got ${deck.size}")
    }

    @Test
    fun dealHandNoTrumpCard() {
        val deckHelper = DeckHelper()

        // Gives 8 cards
        val deck = deckHelper.shuffleDeck(1)

        // 4 players * 2 cards per player = 8 cards
        val (hands, trumpCard) = deckHelper.dealHand(deck, 4, 2)

        assertEquals(4, hands.size, "Expected to have 4 players, but only got ${hands.size} hands dealt.")
        assertEquals(2, hands[0].cards.size, "Should have had 2 cards per hand, but a hand had ${hands[0].cards.size} cards instead.")
        assertNull(trumpCard) // Shouldn't have any cards left, so no trump card
    }

}