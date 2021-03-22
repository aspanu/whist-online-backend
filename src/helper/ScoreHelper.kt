package com.aspanu.whistOnline.helper

import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.Round
import com.aspanu.whistOnline.model.RoundScore
import com.aspanu.whistOnline.model.Scoreboard

class ScoreHelper {
    private val correctBidPoints = 5
    /*
     Scores:
     0 -> 5
     1 -> 6
     2 -> 8
     3 -> 11
     4 -> 15
     5 -> 20
     6 -> 26
     7 -> 33
     8 -> 41

     -1 -> -1
     -2 -> -3
     -3 -> -6
     -4 -> -10
     -5 -> -15
     -6 -> -21
     -7 -> -28
     -8 -> -36
     */

    fun calculateScore(bidAmount: Int, differenceFromBid: Int): Int {
        return if (differenceFromBid == 0) {
            val rawScoreChange = (bidAmount * (bidAmount + 1)) / 2
            rawScoreChange + correctBidPoints // Score amount
        } else {
            val rawScoreChange = differenceFromBid * (differenceFromBid + 1) / 2
            0 - rawScoreChange
        }
    }

    fun createScoreboard(players: List<Player>): Scoreboard {
        val numPlayers = players.size
        val numTrickRange = mutableListOf<Int>()
        repeat(numPlayers) { numTrickRange.add(1) }
        numTrickRange.addAll(2..7)
        repeat(numPlayers) { numTrickRange.add(8) }
        numTrickRange.addAll(7 downTo 2)
        repeat(numPlayers) { numTrickRange.add(1) }

        val rounds = mutableListOf<Round>()
        for ((currentRoundNumber, numTricks) in numTrickRange.withIndex()) {
            val firstPlayer = currentRoundNumber % numPlayers
            rounds.add(Round(firstPlayer, numTricks, (0 until numPlayers).map { RoundScore() }))
        }

        return Scoreboard(players, rounds)
    }

    fun addBid(scoreboard: Scoreboard, player: Player, bid: Int) {
        val playerIndex = scoreboard.players.indexOf(player)

        if (bid < 0) {
            // Bids cannot be negative
            throw IllegalArgumentException("A bid cannot be negative.")
        }

        if (bid > scoreboard.rounds[scoreboard.currentRound].numTricks) {
            // Bids cannot be larger than the current number of tricks
            throw IllegalArgumentException("A bid cannot be larger than the number of tricks available.")
        }

        if (scoreboard.rounds[scoreboard.currentRound].scores[playerIndex].bid >= 0) {
            // Player has already bid once, this is an error
            throw IllegalArgumentException("This player has already made a bid.")
        }

        scoreboard.rounds[scoreboard.currentRound].scores[playerIndex].bid = bid
    }
}