package service

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.Trick
import com.aspanu.whistOnline.service.TrickHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class TrickHelperTest {
    private val trickHelper = TrickHelper()
    private val playerOne = Player(1)
    private val playerTwo = Player(2)
    private val playerThree = Player(3)

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
                playerCards = mapOf(playerOne to Card.ACE_SPADES, playerTwo to Card.ACE_CLUBS, playerThree to Card.KING_SPADES),
                trumpCard = trumpCard,
            )
            DynamicTest.dynamicTest("with player order: $playerOrder and trump card: $trumpCard, $expectedWinningPlayer should win") {
                assertEquals(expectedWinningPlayer, trickHelper.trickWinner(trick))
            }
        }
}