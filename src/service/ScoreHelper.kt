package com.aspanu.whistOnline.service

class ScoreHelper {
    private val correctBidPoints = 5
    /*
     Scores:
     0 -> 5
     1 -> 6
     2 -> 8
     3 -> 11
     4 -> 15
     5 -> 20
     6 -> 26
     7 -> 33
     8 -> 41

     -1 -> -1
     -2 -> -3
     -3 -> -6
     -4 -> -10
     -5 -> -15
     -6 -> -21
     -7 -> -28
     -8 -> -36
     */

    fun calculateScore(bidAmount: Int, differenceFromBid: Int): Int {
        return if (differenceFromBid == 0) {
            val rawScoreChange = (bidAmount * (bidAmount + 1)) / 2
            rawScoreChange + correctBidPoints // Score amount
        } else {
            val rawScoreChange = differenceFromBid * (differenceFromBid + 1) / 2
            0 - rawScoreChange
        }
    }
}