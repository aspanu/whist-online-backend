package helper

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.PlayedCards
import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.Trick
import com.aspanu.whistOnline.helper.TrickHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class TrickHelperTest {
    private val trickHelper = TrickHelper()
    private val playerOne = Player(1)
    private val playerTwo = Player(2)
    private val playerThree = Player(3)
    private val playerFour = Player(4)

    private val testData = listOf(
        Triple(listOf(playerOne, playerTwo, playerThree), null, playerOne),
        Triple(listOf(playerTwo, playerOne, playerThree), null, playerTwo),
        Triple(listOf(playerThree, playerOne, playerTwo), null, playerOne),
        Triple(listOf(playerTwo, playerOne, playerThree), Card.KING_CLUBS, playerTwo),
        Triple(listOf(playerOne, playerTwo, playerThree), Card.KING_CLUBS, playerTwo),
    )

    @TestFactory
    fun testTrickWinner() = testData
        .map { (playerOrder, trumpCard, expectedWinningPlayer) ->
            val trick = Trick(
                playerOrder = playerOrder,
                playerCards = PlayedCards(mutableMapOf(playerOne to Card.ACE_SPADES, playerTwo to Card.ACE_CLUBS, playerThree to Card.KING_SPADES)),
                trumpCard = trumpCard,
            )
            DynamicTest.dynamicTest("with player order: $playerOrder and trump card: $trumpCard, $expectedWinningPlayer should win") {
                assertEquals(expectedWinningPlayer, trickHelper.trickWinner(trick))
            }
        }


    @Test
    fun testTrickWinnerEdgeCase() {
        val playerOrder = listOf(playerOne, playerTwo, playerThree, playerFour)
        val playerCards = PlayedCards(mutableMapOf(playerOne to Card.EIGHT_CLUBS, playerTwo to Card.KING_SPADES, playerThree to Card.JACK_SPADES, playerFour to Card.ACE_HEARTS))
        val trumpCard = Card.ACE_SPADES

        val trick = Trick(playerOrder, playerCards, trumpCard)
        val winner = trickHelper.trickWinner(trick)
        assertEquals(playerTwo, winner, "Expected $playerTwo to win, but instead got $winner")
    }

    @Test
    fun testTrickScores() {
        val trickResults = mapOf(playerOne to Pair(0, 0), playerTwo to Pair(3, 1), playerThree to Pair(1,7))
        val scoreChanges = trickHelper.calculateScoreChanges(trickResults)
        assertEquals(3, scoreChanges.size)
        assertEquals(5, scoreChanges[playerOne])
        assertEquals(-3, scoreChanges[playerTwo])
        assertEquals(-21, scoreChanges[playerThree])
    }
}