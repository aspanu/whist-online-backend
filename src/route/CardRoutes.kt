package com.aspanu.whistOnline.route

import com.aspanu.whistOnline.model.handStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

/**
 * Created by aspanu on 2021-01-18.
 */

fun Route.cardRouting() {
    route("/hand") {
        get {
            if (handStorage.isNotEmpty()) {
                call.respond(handStorage)
            } else {
                call.respondText("No customers found", status= HttpStatusCode.BadRequest)
            }
        }
        get("{playerId}") {
            val playerIdStr = call.parameters["playerId"] ?: return@get call.respondText(
                "Missing or malformed id.",
                status = HttpStatusCode.BadRequest
            )
            val playerId = playerIdStr.toInt()
            val hand = handStorage[playerId] ?: return@get call.respondText(
                "No hand for player: $playerId",
                status = HttpStatusCode.NotFound
            )

            call.respond(hand)
        }
    }
}

fun Application.registerCardRoutes() {
    routing {
        cardRouting()
    }
}