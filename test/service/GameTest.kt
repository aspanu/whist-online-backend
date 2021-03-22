package service

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.service.Game
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GameTest {

    @Test
    fun testPlayingCards() {
        val game = Game(3)
        game.playCard(game.gameScoreboard.players[0], Card.ACE_DIAMONDS)
        game.playCard(game.gameScoreboard.players[1], Card.ACE_HEARTS)
        game.playCard(game.gameScoreboard.players[2], Card.KING_DIAMONDS)

        assertEquals(3, game.currentTrickHand.cards.size)
        assertEquals(Card.ACE_DIAMONDS, game.currentTrickHand.cards[game.gameScoreboard.players[0]])
        assertEquals(Card.ACE_HEARTS, game.currentTrickHand.cards[game.gameScoreboard.players[1]])
        assertEquals(Card.KING_DIAMONDS, game.currentTrickHand.cards[game.gameScoreboard.players[2]])
    }
}