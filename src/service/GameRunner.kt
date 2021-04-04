package com.aspanu.whistOnline.service

import com.aspanu.whistOnline.helper.UserHelper
import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.Player
import com.aspanu.whistOnline.model.User
import io.ktor.http.cio.websocket.*
import java.util.*

// Contains the knowledge of a single game - the players, maintains their game state and used
// by the API to retain the player channels
class GameRunner {
    private val connectionUsers: MutableMap<DefaultWebSocketSession, User> = Collections.synchronizedMap(mutableMapOf<DefaultWebSocketSession, User>())
    private val userToPlayer = mutableMapOf<User, Player>()
    private val game: Game by lazy { Game(connectionUsers.size) }
    private val userHelper = UserHelper()


    fun addUser(socket: DefaultWebSocketSession) {
        connectionUsers[socket] = userHelper.createUser()
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
    }

    private fun getPlayer(socket: DefaultWebSocketSession) = userToPlayer[
            connectionUsers[socket] ?: throw IllegalArgumentException("Socket isn't part of the game")
    ] ?: throw IllegalArgumentException("User wasn't previously playing")
}

