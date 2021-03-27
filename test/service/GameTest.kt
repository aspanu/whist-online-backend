package service

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.service.Game
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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

    @Test
    fun playAGame() {
        val game = Game(4)
        val hand = game.dealNextRound()

        val player0 = game.scores.players[0]
        val player1 = game.scores.players[1]
        val player2 = game.scores.players[2]
        val player3 = game.scores.players[3]

        assertTrue(hand.playerHands[player0]?.cards?.size ?: 0 == 1)
        assertTrue(hand.playerHands[player1]?.cards?.size ?: 0 == 1)
        assertTrue(hand.playerHands[player2]?.cards?.size ?: 0 == 1)
        assertTrue(hand.playerHands[player3]?.cards?.size ?: 0 == 1)

        game.bid(player0, bidAmount = 0)
        game.bid(player1, bidAmount = 1)
        game.bid(player2, bidAmount = 0)
        game.bid(player3, bidAmount = 1)

        assertTrue(hand.trumpCard !== null)

        // Player 1 is going to be the winner
        game.playCard(player0, card = Card.TWO_HEARTS)
        game.playCard(player1, card = hand.trumpCard!!)
        game.playCard(player2, card = Card.TWO_CLUBS)
        game.playCard(player3, card = Card.TWO_DIAMONDS)

        assertEquals(0, game.scores.rounds[game.scores.currentRound-1].scores[0].bid)
        assertEquals(1, game.scores.rounds[game.scores.currentRound-1].scores[1].bid)
        assertEquals(0, game.scores.rounds[game.scores.currentRound-1].scores[2].bid)
        assertEquals(1, game.scores.rounds[game.scores.currentRound-1].scores[3].bid)

        assertEquals(5, game.scores.rounds[game.scores.currentRound-1].scores[0].score)
        assertEquals(6, game.scores.rounds[game.scores.currentRound-1].scores[1].score)
        assertEquals(5, game.scores.rounds[game.scores.currentRound-1].scores[2].score)
        assertEquals(-1, game.scores.rounds[game.scores.currentRound-1].scores[3].score)
    }
}