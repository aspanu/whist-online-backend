package com.aspanu.whistOnline.model

data class Trick(val playerOrder: List<Player>, val playerCards: Map<Player, Card>, val trumpCard: Card? = null)