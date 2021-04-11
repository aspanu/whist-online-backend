package helper

import com.aspanu.whistOnline.helper.PlayerHelper
import com.aspanu.whistOnline.helper.ScoreHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows

class ScoreHelperTest {
    private val scoreHelper = ScoreHelper()

    @Test
    fun testCorrectScoring() {
        assertEquals(5, scoreHelper.calculateScore(0,0))
        assertEquals(6, scoreHelper.calculateScore(1,0))
        assertEquals(8, scoreHelper.calculateScore(2,0))
        assertEquals(11, scoreHelper.calculateScore(3,0))
        assertEquals(15, scoreHelper.calculateScore(4,0))
        assertEquals(20, scoreHelper.calculateScore(5,0))
        assertEquals(26, scoreHelper.calculateScore(6,0))
        assertEquals(33, scoreHelper.calculateScore(7,0))
        assertEquals(41, scoreHelper.calculateScore(8,0))
    }

    @Test
    fun testIncorrectScoring() {
        assertEquals(-1, scoreHelper.calculateScore(0,1))
        assertEquals(-3, scoreHelper.calculateScore(0,2))
        assertEquals(-6, scoreHelper.calculateScore(0,3))
        assertEquals(-10, scoreHelper.calculateScore(0,4))
        assertEquals(-15, scoreHelper.calculateScore(0,5))
        assertEquals(-21, scoreHelper.calculateScore(0,6))
        assertEquals(-28, scoreHelper.calculateScore(0,7))
        assertEquals(-36, scoreHelper.calculateScore(0,8))
    }

    @Test
    fun testCreateScoreboard() {
        val numPlayers = 4
        val playerHelper = PlayerHelper()
        val players = playerHelper.initializePlayers(numPlayers)

        val scoreboard = scoreHelper.createScoreboard(players)

        val numRounds = 12 + (3 * numPlayers)
        assertEquals(numRounds, scoreboard.rounds.size)
        assertEquals(0, scoreboard.currentRound)
        assertEquals(4, scoreboard.players.size)

        var currentRound = 0
        for (i in 0 until numPlayers) {
            assertEquals(1, scoreboard.rounds[currentRound].numTricks)
            assertEquals(i, scoreboard.rounds[currentRound].firstPlayer)
            currentRound++
        }

        for (i in 2..7) {
            assertEquals(i, scoreboard.rounds[currentRound].numTricks)
            assertEquals(currentRound % numPlayers, scoreboard.rounds[currentRound].firstPlayer)
            currentRound++
        }

        for (i in 0 until numPlayers) {
            assertEquals(8, scoreboard.rounds[currentRound].numTricks)
            assertEquals(currentRound % numPlayers, scoreboard.rounds[currentRound].firstPlayer)
            currentRound++
        }

        for (i in 7 downTo 2) {
            assertEquals(i, scoreboard.rounds[currentRound].numTricks)
            assertEquals(currentRound % numPlayers, scoreboard.rounds[currentRound].firstPlayer)
            currentRound++
        }

        for (i in 0 until numPlayers) {
            assertEquals(1, scoreboard.rounds[currentRound].numTricks)
            assertEquals(currentRound % numPlayers, scoreboard.rounds[currentRound].firstPlayer)
            currentRound++
        }
    }

    @Test
    fun testAddingBid() {
        val playerHelper = PlayerHelper()
        val players = playerHelper.initializePlayers(3)
        val scoreboard = scoreHelper.createScoreboard(players)
        scoreHelper.addBid(scoreboard, players[0], 0)
        scoreHelper.addBid(scoreboard, players[1], 1)
        scoreHelper.addBid(scoreboard, players[2], 0)

        assertEquals(0, scoreboard.rounds[scoreboard.currentRound].scores[0].bid)
        assertEquals(1, scoreboard.rounds[scoreboard.currentRound].scores[1].bid)
        assertEquals(0, scoreboard.rounds[scoreboard.currentRound].scores[2].bid)
    }

    @Test
    fun testFailingAtAddingBid() {
        val playerHelper = PlayerHelper()
        val players = playerHelper.initializePlayers(5)
        val scoreboard = scoreHelper.createScoreboard(players)
        assertThrows(IllegalArgumentException::class.java) { scoreHelper.addBid(scoreboard, players[3], -1) }
        assertThrows(IllegalArgumentException::class.java) { scoreHelper.addBid(scoreboard, players[3], 12) }
        scoreHelper.addBid(scoreboard, players[3], 0)
        assertThrows(IllegalArgumentException::class.java) { scoreHelper.addBid(scoreboard, players[3], 0) }
        scoreHelper.addBid(scoreboard, players[0], 0)
        scoreHelper.addBid(scoreboard, players[1], 0)
        scoreHelper.addBid(scoreboard, players[2], 0)
        assertThrows(IllegalStateException::class.java) { scoreHelper.addBid(scoreboard, players[4], 1) }
    }
}