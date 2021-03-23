package service

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.service.Game
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GameTest {

    @Test
    fun testPlayingCards() {
        val game = Game(3)
        game.playCard(game.scores.players[0], Card.ACE_DIAMONDS)
        game.playCard(game.scores.players[1], Card.ACE_HEARTS)

        assertEquals(2, game.currentTrickHand.cards.size)
        assertEquals(Card.ACE_DIAMONDS, game.currentTrickHand.cards[game.scores.players[0]])
        assertEquals(Card.ACE_HEARTS, game.currentTrickHand.cards[game.scores.players[1]])
    }

//    @Test
    fun playAGame() {
        TODO("Implement a full integration test.")
    }
}