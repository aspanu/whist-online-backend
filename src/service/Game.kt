package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.helper.*
import com.aspanu.whistOnline.model.*

class Game(private val numPlayers: Int) {
    // Contains the logic of a single game
    // Has the number of players, and the current score

    private val playerHelper = PlayerHelper()
    private val scoreHelper = ScoreHelper()
    private val trickHelper = TrickHelper()
    private val deckHelper = DeckHelper()

    val scores : Scoreboard
    var currentTrickHand = PlayedCards(mutableMapOf())
    var currentRoundTricks = mutableMapOf<Player, Int>()
    var currentRoundTrump : Card? = null
    var finalPlayerScores = mapOf<Player, Int>()
    var gameComplete = false
    private val deck : List<Card>

    init {
        // Initialize state:
        // Create players
        // Figure out player order
        // Create scoreboard for players in order
        // Deal the deck

        val initializedPlayers = playerHelper.initializePlayers(numPlayers)

        // Players created, time to shuffle them
        val orderedPlayers = playerHelper.randomizePlayerOrder(initializedPlayers)

        //With shuffled players, create the scoreboard
        scores = scoreHelper.createScoreboard(orderedPlayers)

        deck = deckHelper.shuffleDeck(numPlayers)
    }

    fun bid(player: Player, bidAmount: Int) {
        scoreHelper.addBid(scores, player, bidAmount)
    }

    fun playCard(player: Player, card: Card) {
        if (currentTrickHand.cards.containsKey(player)) {
            throw IllegalArgumentException("Player has already played a card.")
        }

        currentTrickHand.cards[player] = card

        // If everyone has played their cards, we can finalize this trick
        if (currentTrickHand.cards.size == numPlayers) {
            finalizeTrick()
        }

    }

    private fun finalizeTrick() {
        // Figure out who won this trick, add it to the list of tricks and then move to the trick
        val playerOrder = playerHelper.getPlayersInOrder(scores.players, scores.rounds[scores.currentRound].firstPlayer)
        val trick = Trick(playerOrder= playerOrder, playerCards = currentTrickHand, trumpCard = currentRoundTrump)

        val winningPlayer = trickHelper.trickWinner(trick)
        currentRoundTricks[winningPlayer] = (currentRoundTricks[winningPlayer] ?: 0).plus(1)

        // If this is the last trick of the round, then finish the round as well
        if (currentRoundTricks.values.sum() == scores.rounds[scores.currentRound].numTricks) {
            finalizeRound()
        }

        currentTrickHand = PlayedCards(mutableMapOf())
    }

    private fun finalizeRound() {
        // Figure out the scores, add them to the scoreboard, and advance the round, and check to see if the game is over
        // Create a "playerBidTricks", get the new scores, update them

        val playerBidTricks = mutableMapOf<Player, Pair<Int, Int>>()
        scores.players.forEach {
            val bid = scores.getBid(it, scores.currentRound)
            playerBidTricks[it] = Pair(bid, currentRoundTricks[it] ?: 0) // If the player didn't make any tricks, they will have 0
        }
        val playerWinnings = trickHelper.calculateScoreChanges(playerBidTricks)

        playerWinnings.keys.forEach {
            scores.addScore(it, scores.currentRound, playerWinnings[it] ?: 0)
        }

        // Reset round
        scores.currentRound++
        currentRoundTricks = mutableMapOf()

        if (scores.currentRound >= scores.rounds.size) {
            finalizeGame()
        }
    }

    private fun finalizeGame() {
        // Add the 'finish game' logic, which is really just making sure no more hands can be dealt and the score is tallied
        finalPlayerScores = scores.players.mapIndexed { i, player -> player to scores.rounds.last().scores[i].score }.toMap()
        gameComplete = true
    }

    fun dealNextRound(): HandDealt {
        return deckHelper.dealHandToPlayers(deck, scores.players, scores.rounds[scores.currentRound].numTricks)
    }
}