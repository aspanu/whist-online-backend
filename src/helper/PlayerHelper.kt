package com.aspanu.whistOnline.helper

import com.aspanu.whistOnline.model.Player
import kotlin.random.Random

class PlayerHelper {
    fun initializePlayers(numPlayers: Int): List<Player> {
        return (0 until numPlayers).map { Player(it) }
    }

    fun randomizePlayerOrder(players: List<Player>): List<Player> {
        return players.shuffled()
    }

    fun getPlayersInOrder(players: List<Player>, firstPlayer: Int): List<Player> {
        val newOrder = mutableListOf<Player>()
        for (i in firstPlayer until firstPlayer + players.size) {
            newOrder.add(players[i % players.size])
        }
        return newOrder
    }
}
