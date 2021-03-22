package com.aspanu.whistOnline.model

data class Scoreboard(val players: List<Player>, val rounds: List<Round>, var currentRound: Int = 0)
data class Round(val firstPlayer: Int, val numTricks: Int, val scores: List<RoundScore>)
data class RoundScore(var bid: Int = -1, var score: Int = -1)