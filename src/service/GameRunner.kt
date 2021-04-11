package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.helper.HandDealt
import com.aspanu.whistOnline.helper.UserHelper
import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.Hand
import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.User
import io.ktor.http.cio.websocket.*
import io.ktor.websocket.*
import java.util.*

// Contains the knowledge of a single game - the players, maintains their game state and used
// by the API to retain the player channels
class GameRunner {
    private val connectionUsers: MutableMap<DefaultWebSocketSession, User> = Collections.synchronizedMap(mutableMapOf<DefaultWebSocketSession, User>())
    private val userToPlayer = mutableMapOf<User, Player>()
    private val userHelper = UserHelper()
    private val game: Game by lazy { Game(connectionUsers.size) }

    private var currentHand: HandDealt? = null // TODO: make this synchronized to work between threads
    private var handCardsPlayed = 0 // Keep track of the game and reset at the end of each round

    fun addUser(socket: DefaultWebSocketSession): User {
        val user = userHelper.createUser()
        connectionUsers[socket] = user
        return user
    }

    fun startGame() {
        connectionUsers.values.forEachIndexed { index, user -> userToPlayer[user] = game.scores.players[index] }
    }

    fun makeBid(socket: DefaultWebSocketSession, bid: Int) {
        val player = getPlayer(socket)
        game.bid(player, bid)
    }

    fun playCard(socket: DefaultWebSocketSession, card: Card) {
        val player = getPlayer(socket)
        game.playCard(player, card)
        handCardsPlayed++
        if (handCardsPlayed == userToPlayer.size) {
            currentHand = null
            handCardsPlayed = 0
        }
    }

    fun userIdToUser(userId: Int) : User {
        return userHelper.getUserForId(userId)
    }

    fun dealNextHand(user: User): Pair<Hand, Card?> {
        if (currentHand == null) {
            currentHand = game.dealNextRound()
        }

        val player = userToPlayer[user] ?: throw IllegalArgumentException("User wasn't previously playing")
        return Pair(
            currentHand?.playerHands?.get(player) ?: throw IllegalStateException("Current player doesn't have a hand."),
            currentHand?.trumpCard
        )
    }

    fun getOtherSockets(socket: DefaultWebSocketSession): Collection<DefaultWebSocketSession> {
        return connectionUsers.keys.filter { it != socket }
    }

    private fun getPlayer(socket: DefaultWebSocketSession) = userToPlayer[
            connectionUsers[socket] ?: throw IllegalArgumentException("Socket isn't part of the game")
    ] ?: throw IllegalArgumentException("User wasn't previously playing")

    fun removeUser(socket: DefaultWebSocketServerSession) {
        val user = connectionUsers[socket] ?: return
        connectionUsers.remove(socket)
        userToPlayer.remove(user)
    }
}

