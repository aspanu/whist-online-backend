package com.aspanu.whistOnline.model

val handStorage = mutableMapOf(0 to Hand(listOf(Card.ACE_DIAMONDS, Card.ACE_HEARTS)))

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
    TWO_CLUBS(1, Suit.CLUBS),
    TWO_DIAMONDS(1, Suit.DIAMONDS),
    TWO_HEARTS(1, Suit.HEARTS),
    TWO_SPADES(1, Suit.SPADES),
    THREE_CLUBS(2, Suit.CLUBS),
    THREE_DIAMONDS(2, Suit.DIAMONDS),
    THREE_HEARTS(2, Suit.HEARTS),
    THREE_SPADES(2, Suit.SPADES),
    FOUR_CLUBS(3, Suit.CLUBS),
    FOUR_DIAMONDS(3, Suit.DIAMONDS),
    FOUR_HEARTS(3, Suit.HEARTS),
    FOUR_SPADES(3, Suit.SPADES),
    FIVE_CLUBS(4, Suit.CLUBS),
    FIVE_DIAMONDS(4, Suit.DIAMONDS),
    FIVE_HEARTS(4, Suit.HEARTS),
    FIVE_SPADES(4, Suit.SPADES),
    SIX_CLUBS(5, Suit.CLUBS),
    SIX_DIAMONDS(5, Suit.DIAMONDS),
    SIX_HEARTS(5, Suit.HEARTS),
    SIX_SPADES(5, Suit.SPADES),
    SEVEN_CLUBS(6, Suit.CLUBS),
    SEVEN_DIAMONDS(6, Suit.DIAMONDS),
    SEVEN_HEARTS(6, Suit.HEARTS),
    SEVEN_SPADES(6, Suit.SPADES),
    EIGHT_CLUBS(7, Suit.CLUBS),
    EIGHT_DIAMONDS(7, Suit.DIAMONDS),
    EIGHT_HEARTS(7, Suit.HEARTS),
    EIGHT_SPADES(7, Suit.SPADES),
    NINE_CLUBS(8, Suit.CLUBS),
    NINE_DIAMONDS(8, Suit.DIAMONDS),
    NINE_HEARTS(8, Suit.HEARTS),
    NINE_SPADES(8, Suit.SPADES),
    TEN_CLUBS(9, Suit.CLUBS),
    TEN_DIAMONDS(9, Suit.DIAMONDS),
    TEN_HEARTS(9, Suit.HEARTS),
    TEN_SPADES(9, Suit.SPADES),
    JACK_CLUBS(10, Suit.CLUBS),
    JACK_DIAMONDS(10, Suit.DIAMONDS),
    JACK_HEARTS(10, Suit.HEARTS),
    JACK_SPADES(10, Suit.SPADES),
    QUEEN_CLUBS(11, Suit.CLUBS),
    QUEEN_DIAMONDS(11, Suit.DIAMONDS),
    QUEEN_HEARTS(11, Suit.HEARTS),
    QUEEN_SPADES(11, Suit.SPADES),
    KING_CLUBS(12, Suit.CLUBS),
    KING_DIAMONDS(12, Suit.DIAMONDS),
    KING_HEARTS(12, Suit.HEARTS),
    KING_SPADES(12, Suit.SPADES),
    ACE_CLUBS(13, Suit.CLUBS),
    ACE_DIAMONDS(13, Suit.DIAMONDS),
    ACE_HEARTS(13, Suit.HEARTS),
    ACE_SPADES(13, Suit.SPADES),
}
