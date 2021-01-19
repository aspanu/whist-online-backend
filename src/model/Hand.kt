package com.aspanu.whistOnline.model

val handStorage = mutableMapOf<Int,Hand>(0 to Hand(listOf(Card.ACE_OF_DIAMONDS, Card.ACE_OF_HEARTS)))

data class Hand(val cards: List<Card>) {
}

enum class Card {
    ACE_OF_DIAMONDS,
    ACE_OF_SPADES,
    ACE_OF_HEARTS,
}