package service

import com.aspanu.whistOnline.service.PlayerHelper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
}