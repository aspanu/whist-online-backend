package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.model.Player

class PlayerHelper {
    fun initializePlayers(numPlayers: Int): List<Player> {
        return (0 until numPlayers).map { Player(it) }
    }

    fun randomizePlayerOrder(players: List<Player>): List<Player> {
        return players.shuffled()
    }
}
