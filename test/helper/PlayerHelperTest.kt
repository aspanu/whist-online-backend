package helper

import com.aspanu.whistOnline.helper.PlayerHelper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PlayerHelperTest {
    private val playerHelper = PlayerHelper()

    @Test
    fun testPlayerInitialization() {
        val players = playerHelper.initializePlayers(3)
        assertEquals(3, players.size)
    }

    @Test
    fun testRandomizeOrder() {
        val players = playerHelper.initializePlayers(numPlayers = 3)
        val newOrder = playerHelper.randomizePlayerOrder(players)
        players.forEach { assertTrue(newOrder.contains(it)) }
        newOrder.forEach { assertTrue(players.contains(it)) }
    }

    @Test
    fun testGetPlayersInOrder() {
        val players = playerHelper.initializePlayers(3)
        var newOrder = playerHelper.getPlayersInOrder(players, 0)
        assertEquals(players[0], newOrder[0])
        assertEquals(players[1], newOrder[1])
        assertEquals(players[2], newOrder[2])


        newOrder = playerHelper.getPlayersInOrder(players, 1)
        assertEquals(players[1], newOrder[0])
        assertEquals(players[2], newOrder[1])
        assertEquals(players[0], newOrder[2])

        newOrder = playerHelper.getPlayersInOrder(players, 2)
        assertEquals(players[2], newOrder[0])
        assertEquals(players[0], newOrder[1])
        assertEquals(players[1], newOrder[2])

    }
}