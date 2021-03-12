package com.aspanu.whistOnline.service

class GameRunner(val numPlayers: Int) {
    // Contains the logic of the game
    // Has the number of players, and the current score
    private var gameStarted = true
    private val deckHelper = DeckHelper()
    private val deck = deckHelper.shuffleDeck(this.numPlayers)
    private val playerHelper = PlayerHelper()
    private val players = playerHelper.initializePlayers(this.numPlayers)

}