package helper

import com.aspanu.whistOnline.helper.DeckHelper
import com.aspanu.whistOnline.helper.PlayerHelper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import kotlin.test.assertNotNull

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
        val playerHelper = PlayerHelper()

        // Gives 8 cards
        val deck = deckHelper.shuffleDeck(1)

        val players = playerHelper.initializePlayers(4)

        // 4 players * 2 cards per player = 8 cards
        val (hands, trumpCard) = deckHelper.dealHandToPlayers(deck, players, 2)

        assertEquals(4, hands.size, "Expected to have 4 players, but only got ${hands.size} hands dealt.")
        assertEquals(2, hands[players[0]]!!.cards.size, "Should have had 2 cards per hand, but a hand had ${hands[players[0]]!!.cards.size} cards instead.")
        assertNull(trumpCard) // Shouldn't have any cards left, so no trump card
    }

    @Test
    fun dealHandTrumpCard() {
        val deckHelper = DeckHelper()
        val playerHelper = PlayerHelper()

        val deck = deckHelper.shuffleDeck(1)

        val players = playerHelper.initializePlayers(4)

        val (hands, trumpCard) = deckHelper.dealHandToPlayers(deck, players, 1)

        assertEquals(4, hands.size)
        assertEquals(1, hands[players[0]]!!.cards.size)
        assertNotNull(trumpCard)
    }

}