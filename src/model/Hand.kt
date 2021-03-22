package com.aspanu.whistOnline.model

val handStorage = mutableMapOf<Int,Hand>(0 to Hand(listOf(Card.ACE_DIAMONDS, Card.ACE_HEARTS)))

data class Hand(val cards: List<Card>) {
}

data class PlayedCards(val cards: MutableMap<Player, Card>)

enum class Suit {
    CLUBS,
    DIAMONDS,
    HEARTS,
    SPADES,
}

enum class Card(val magnitude: Int, val suit: Suit) {

    KING_CLUBS(12, Suit.CLUBS),
    KING_DIAMONDS(12, Suit.DIAMONDS),
    KING_HEARTS(12, Suit.HEARTS),
    KING_SPADES(12, Suit.SPADES),
    ACE_CLUBS(13, Suit.CLUBS),
    ACE_DIAMONDS(13, Suit.DIAMONDS),
    ACE_HEARTS(13, Suit.HEARTS),
    ACE_SPADES(13, Suit.SPADES),
    TWO_CLUBS(0, Suit.CLUBS),

/*
    TWO_DIAMONDS,
    TWO_HEARTS,
    TWO_SPADES,
    THREE_CLUBS,
    THREE_DIAMONDS,
    THREE_HEARTS,
    THREE_SPADES,
    FOUR_CLUBS,
    FOUR_DIAMONDS,
    FOUR_HEARTS,
    FOUR_SPADES,
    FIVE_CLUBS,
    FIVE_DIAMONDS,
    FIVE_HEARTS,
    FIVE_SPADES,
    SIX_CLUBS,
    SIX_DIAMONDS,
    SIX_HEARTS,
    SIX_SPADES,
    SEVEN_CLUBS,
    SEVEN_DIAMONDS,
    SEVEN_HEARTS,
    SEVEN_SPADES,
    EIGHT_CLUBS,
    EIGHT_DIAMONDS,
    EIGHT_HEARTS,
    EIGHT_SPADES,
    NINE_CLUBS,
    NINE_DIAMONDS,
    NINE_HEARTS,
    NINE_SPADES,
    TEN_CLUBS,
    TEN_DIAMONDS,
    TEN_HEARTS,
    TEN_SPADES,
    JACK_CLUBS,
    JACK_DIAMONDS,
    JACK_HEARTS,
    JACK_SPADES,
    QUEEN_CLUBS,
    QUEEN_DIAMONDS,
    QUEEN_HEARTS,
    QUEEN_SPADES,
 */

}
