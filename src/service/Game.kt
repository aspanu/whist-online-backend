package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.helper.ScoreHelper
import com.aspanu.whistOnline.helper.PlayerHelper
import com.aspanu.whistOnline.helper.TrickHelper
import com.aspanu.whistOnline.model.*

class Game(private val numPlayers: Int) {
    // Contains the logic of a single game
    // Has the number of players, and the current score

    private val playerHelper = PlayerHelper()
    private val scoreHelper = ScoreHelper()
    private val trickHelper = TrickHelper()

    val gameScoreboard : Scoreboard
    var currentTrickHand = PlayedCards(mutableMapOf())
    var currentRoundTricks = mutableMapOf<Player, Int>()
    var currentRoundTrump : Card? = null

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

    fun playCard(player: Player, card: Card) {
        if (currentTrickHand.cards.containsKey(player)) {
            throw IllegalArgumentException("Player has already played a card.")
        }

        currentTrickHand.cards[player] = card

        // If everyone has played their cards, we can finalize this trick
        if (currentTrickHand.cards.size == numPlayers) {
            // Figure out who won this trick, add it to the list of tricks and then move to the trick
            val playerOrder = playerHelper.getPlayersInOrder(gameScoreboard.players, gameScoreboard.rounds[gameScoreboard.currentRound].firstPlayer)
            val trick = Trick(playerOrder= playerOrder, playerCards = currentTrickHand, trumpCard = currentRoundTrump)

            val winningPlayer = trickHelper.trickWinner(trick)
            currentRoundTricks[winningPlayer] = (currentRoundTricks[winningPlayer]?: 0).plus(1)
            TODO("Add a way to check if the round is done too and move the 'trick ending' to a separate function")

            currentTrickHand = PlayedCards(mutableMapOf())
        }
    }

}