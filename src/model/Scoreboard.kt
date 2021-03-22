package com.aspanu.whistOnline.model

data class Scoreboard(val players: List<Player>, val rounds: List<Round>, var currentRound: Int = 0) {
    fun getBid(player: Player, round: Int): Int {
        return rounds[round].scores[players.indexOf(player)].bid
    }

    fun addScore(player: Player, round: Int, scoreToAdd: Int) {
        val playerIndex = players.indexOf(player)
        val previousScore = if (round == 0) 0 else rounds[round].scores[playerIndex].score
        rounds[round].scores[playerIndex].score = scoreToAdd + previousScore
    }
}
data class Round(val firstPlayer: Int, val numTricks: Int, val scores: List<RoundScore>)
data class RoundScore(var bid: Int = -1, var score: Int = -1)