package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.service.PlayerHelper
import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.Scoreboard

class Game(numPlayers: Int) {
    // Contains the logic of a single game
    // Has the number of players, and the current score

    private val playerHelper = PlayerHelper()
    private val scoreHelper = ScoreHelper()

    private val gameScoreboard : Scoreboard

    init {
        // Initialize state:
        // Create players
        // Figure out player order
        // Create scoreboard for players in order
        // Set the current hand being played pointer

        val initializedPlayers = playerHelper.initializePlayers(numPlayers)

        // Players created, time to shuffle them
        val orderedPlayers = playerHelper.randomizePlayerOrder(initializedPlayers)

        //With shuffled players, create the scoreboard
        gameScoreboard = scoreHelper.createScoreboard(orderedPlayers)
    }

    fun bid(player: Player, bidAmount: Int) {
        scoreHelper.addBid(gameScoreboard, player, bidAmount)
    }
    
}