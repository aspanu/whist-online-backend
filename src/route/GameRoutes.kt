package com.aspanu.whistOnline.route

import com.aspanu.whistOnline.model.Card
import com.aspanu.whistOnline.model.handStorage
import com.aspanu.whistOnline.service.GameRunner
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.websocket.*

abstract class Event(val type: String)

@JsonClass(generateAdapter = true)
data class Bid(val bid: Int) : Event("bid")

@JsonClass(generateAdapter = true)
data class CardPlayed(val card: Card) : Event("card")

@JsonClass(generateAdapter = true)
data class StartGame(val start: Boolean): Event("start")

fun Route.gameRouting() {
    val gameRunner = GameRunner()
    val adapterFactory = PolymorphicJsonAdapterFactory.of(Event::class.java, "type")
        .withSubtype(Bid::class.java, "bid")
        .withSubtype(CardPlayed::class.java, "card")
        .withSubtype(StartGame::class.java, "start")
    val moshi = Moshi.Builder().add(adapterFactory).build()

    route("/game/dealNext/{userId}") {
        get {

            val userIdStr = call.parameters["userId"] ?: return@get call.respondText(
                "Missing or malformed user id.",
                status = HttpStatusCode.BadRequest
            )
            val userId = userIdStr.toInt()
            val user = gameRunner.userIdToUser(userId)
            val handTrumpPair = gameRunner.dealNextHand(user)

            call.respond(handTrumpPair)
        }
    }

    webSocket("/game/hello-world") {
        send("Hello World!")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val text = frame.readText()
            send("Echoing: $text")
        }
    }

    // The websocket will communicate back and forth for bids and cards played, but dealing will occur through
    // the rest API above
    webSocket("/game") {
        send("connected")
        gameRunner.addUser(this)
        // Get an event, can be either bid or card played, then send this event to all other sockets in that game
        try {
            while (true) {
                when (val frame = incoming.receive()) {
                    is Frame.Text -> {
                        val json = frame.readText()
                        val event = moshi.adapter(Event::class.java).fromJson(json)
                        println("Got a new event: $json")
                        println("Serialized as: $event")
                        when (event) {
                            is Bid -> {
                                gameRunner.makeBid(this, event.bid)
                                send("Bid made.")
                            }
                            is CardPlayed -> {
                                gameRunner.playCard(this, event.card)
                                send("Card played.")
                            }
                            is StartGame -> {
                                gameRunner.startGame()
                                send("Game started.")
                            }
                        }
                        for (socket in gameRunner.getOtherSockets(this)) {
                            socket.outgoing.send(Frame.Text(json))
                        }
                    }
                    else -> {
                        throw UnsupportedOperationException("Frames must be sent as text.")
                    }
                }
            }
        } catch (exception: Exception) {
            println("Had an error: ${exception.localizedMessage}")
            send("Got error: ${exception.localizedMessage}")
        } finally {
            gameRunner.removeUser(this)
        }

    }
}