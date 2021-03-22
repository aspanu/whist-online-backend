package com.aspanu.whistOnline.model

data class Trick(val playerOrder: List<Player>, val playerCards: PlayedCards, val trumpCard: Card? = null)