package com.aspanu.whistOnline.route

import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.consumeAsFlow
import java.util.*
import kotlin.collections.LinkedHashSet

fun Route.gameRouting() {
    route("/game/new/{playerId}") {
        post {
            TODO("Implement a new game ")
        }
    }
}

fun Route.webSocketRouting() {
    val connections = Collections.synchronizedSet(LinkedHashSet<DefaultWebSocketSession>())

    webSocket("/game/hello-world") {
        send("Hello World!")
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val text = frame.readText()
            send("Echoing: $text")
        }
    }

    webSocket("/game") {
        connections.add(this)
        send("connected")
        TODO("Add the interface connecting the socket messages to the gameRunner calls")
        TODO("Figure out how to respond as well when other players make a bid and play a card")
    }
}